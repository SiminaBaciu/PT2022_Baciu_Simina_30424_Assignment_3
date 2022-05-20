package dao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
/**
 * Class AbstractDAO defines the common operations for accessing a table
 */
public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;


    @SuppressWarnings("unchecked")
    /**
     * Constructor for the abstractDAO class
     */
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Creates a query for selecting everything from a field
     * @param field
     * @return
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Creates a query in order to find all the object from a field
     * @return
     */
    public String findAllQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("*");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Find all function
     * @return
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = findAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Find by ID function
     * @param id
     * @return
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            tryConnection(query);

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
           endConnection();
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Creates a JTable
     * @return
     */
    public JTable createTable() {
        List<T> objects =findAll();
        if (!objects.isEmpty()) {
            int tableSize = objects.get(0).getClass().getDeclaredFields().length;
            String[] columnNames = new String[tableSize];
            int columnIndex = 0;
            for (Field currentField : objects.get(0).getClass().getDeclaredFields()) {
                currentField.setAccessible(true);
                try {
                    columnNames[columnIndex] = currentField.getName();
                    columnIndex++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            DefaultTableModel myModel = new DefaultTableModel();
            myModel.setColumnIdentifiers(columnNames);
            for (Object o : objects) {
                Object[] obj = new Object[tableSize];
                int col = 0;
                for (Field currentField : o.getClass().getDeclaredFields()) {
                    currentField.setAccessible(true);
                    try {
                        obj[col] = currentField.get(o);
                        col++;
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                myModel.addRow(obj);
            }
            JTable table = new JTable(myModel);
            table.setEnabled(true);
            table.setVisible(true);
            return table;
        }
        return null;
    }

    public void tryConnection(String query) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionFactory.getConnection();
        statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();
    }

    public void endConnection(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ConnectionFactory.close(resultSet);
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
    }
    /**
     * Creates a query to insert into a field some values
     * @param object
     * @return
     */
    public String insertQuery(Object object){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT ");
        sb.append("INTO ");
        sb.append(type.getSimpleName());
        sb.append(" VALUES (");
        sb.append(object.toString());
        sb.append(")");
        return sb.toString();
    }

    /**
     * Insert function
     * @param obj
     * @return
     */
    public Object insert(Object obj) {
        String query = insertQuery(obj);
        try {
            tryConnection(query);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
            return obj;
        } finally {
           endConnection();
        }
        return null;
    }


    /**
     * Creates a query in order to update a field
     * @param obj1
     * @param obj2
     * @param category
     * @return
     */
    public String updateQuery(T obj1, T obj2, String category){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        String[] columns = category.split(", ");
        String[] old = obj2.toString().split(", ");
        String[] updates = obj1.toString().split(", ");
        for(int i = 1; i <= columns.length - 1; i++) {
            sb.append(columns[i] + " = " + old[i]);
            if(i != columns.length - 1)
                sb.append(", ");
        }
        sb.append(" WHERE ");
        for(int i = 1; i <= columns.length - 1; i++) {
            sb.append(columns[i] + " = " + updates[i]);
            if(i != columns.length - 1)
                sb.append(" AND ");
        }
        return sb.toString();
    }

    /**
     * Update function
     * @param t1
     * @param t2
     * @param category
     * @return
     */
    public T update(T t1, T t2, String category) {
        String query = updateQuery(t1, t2, category);
        try {
            tryConnection(query);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
            return t1;
        } finally {
         endConnection();
        }
        return null;
    }

    /**
     * Creates a query in order to delete from a field
     * @param delete
     * @return
     */
    private String createDeleteQuery(String delete) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(delete);
        return sb.toString();
    }

    /**
     * delete function
     * @param id
     * @return
     */
    public String delete(String id) {
        String query = createDeleteQuery("id = " + id);
        try {
           tryConnection(query);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
            return "Bad find.";
        } finally {
           endConnection();
        }
        return null;
    }
}


