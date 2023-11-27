package uz.code.todo;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class JDBC {
   static Connection connection = null;


    public void createTask(ToDo toDo) {


        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_jon", "user_jon", "12345"); // <2>
            Statement statement = connection.createStatement(); // <3>
            String sql = "insert into todo(title,content,status) values('%s','%s','%s')";
            sql = String.format(sql, toDo.getTitle(), toDo.getContent(), TaskStatus.ACTIVE);

            int effectedRows = statement.executeUpdate(sql); // <4>
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (connection != null) {
                    System.out.println("new task was created✏️✏️✏️✏️✏️");
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<ToDo> getListTasks() {
        List<ToDo> dtoList = new LinkedList<>();
        try {
             connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_jon", "user_jon", "12345"); // <2>
            Statement statement = connection.createStatement(); // <3>
            ResultSet rs = statement.executeQuery("select id, title,content,status,create_date,finished_date from todo"); // <4>

            while (rs.next()) {
                ToDo dto = new ToDo();
                dto.setId(rs.getInt("id"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setStatus(TaskStatus.valueOf(rs.getString("status")));
                dto.setCreatedDate(rs.getTimestamp("create_date").toLocalDateTime());
                dto.setFinishedDate(rs.getTimestamp("finished_date"));
//                if(rs.getString("status").equals(TaskStatus.DONE.toString())){
//                    Timestamp finishedDate = rs.getTimestamp("finished_date");
//                    if(dto.getFinishedDate()!=null){
//                        dto.setFinishedDate(finishedDate.toLocalDateTime());
//                    }
//                }




                dtoList.add(dto);
            }
            connection.close(); // <5>
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dtoList;
    }

    public void update(Integer id, ToDo dto) {
        try {
             connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_jon", "user_jon", "12345"); // <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update todo set title = '%s', content ='%s',status='%s'  where id = %d";
            sql = String.format(sql, dto.getTitle(), dto.getContent(), TaskStatus.ACTIVE, id);
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Integer id) {
        try {
              connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_jon", "user_jon", "12345"); // <2>
            Statement statement = connection.createStatement(); // <3>

            String sql = "delete from todo where id = " + id;
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
            return effectedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void markAsDone(Integer id) {
        LocalDateTime localDateTime = LocalDateTime.now();
        try {
             connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_jon", "user_jon", "12345"); // <2>
            Statement statement = connection.createStatement(); // <3>
//            String sql = "update student set name = '"+dto.getName()+"', surname ='"+dto.getSurname()+"' where id = "+id;
            String sql = "update todo set status='%s',finished_date=now()  where id = %d";
            sql = String.format(sql, TaskStatus.DONE, id);
            int effectedRows = statement.executeUpdate(sql); // <4>
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
