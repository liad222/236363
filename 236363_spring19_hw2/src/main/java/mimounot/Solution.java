package mimounot;

import mimounot.business.*;

import static mimounot.business.ReturnValue.*;

import mimounot.data.DBConnector;
import mimounot.data.PostgreSQLErrorCodes;

import java.lang.annotation.Retention;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {

    @SuppressWarnings("Duplicates")
    public static void createTables() {

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt1, pstmt2, pstmt3,pstmt4, pstmt5, pstmt6,pstmt7,pstmt8,pstmt9,pstmt10,pstmt11;
        PreparedStatement pstmt12,pstmt13,pstmt14,pstmt15;
        pstmt1 = null;
        pstmt2 = null;
        pstmt3 = null;
        pstmt4 = null;
        pstmt5 = null;
        pstmt6 = null;
        pstmt7 = null;
        pstmt8 = null;
        pstmt9 = null;
        pstmt10 = null;
        pstmt11 = null;
        pstmt12 = null;
        pstmt13 = null;
        pstmt14 = null;
        pstmt15 = null;

        try {

            pstmt1 = connection.prepareStatement("CREATE TABLE Users\n" +
                    "(\n" +
                    "    user_id integer,\n" +
                    "    user_name text NOT NULL,\n" +
                    "    city text NOT NULL,\n" +
                    "    politician boolean NOT NULL,\n" +
                    "    PRIMARY KEY (user_id),\n" +
                    "    CHECK (user_id > 0)\n" +
                    ")");
            pstmt2 = connection.prepareStatement("CREATE TABLE Mimouna\n" +
                    "(\n" +
                    "    mimouna_id integer,\n" +
                    "    user_name text NOT NULL,\n" +
                    "    family_name text NOT NULL,\n" +
                    "    city text NOT NULL,\n" +
                    "    guests_counter integer,\n" +
                    "    is_politician_coming boolean,\n" +
                    "    PRIMARY KEY (mimouna_id),\n" +
                    "    CHECK (mimouna_id > 0),\n" +
                    "    CHECK (guests_counter >= 0)\n" +
                    ")");
            pstmt3 = connection.prepareStatement("CREATE TABLE MimounaList\n" +
                    "(\n" +
                    "    list_id integer,\n" +
                    "    city text NOT NULL,\n" +
                    "    PRIMARY KEY (list_id),\n" +
                    "    CHECK (list_id > 0)\n" +
                    ")");
            pstmt4 = connection.prepareStatement("CREATE TABLE Attending\n" +
                    "(\n" +
                    "    user_id integer,\n" +
                    "FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,\n" +
                    "    mimouna_id integer,\n" +
                    "FOREIGN KEY (mimouna_id) REFERENCES Mimouna(mimouna_id) ON DELETE CASCADE\n" +
                    ")");
            pstmt5 = connection.prepareStatement("CREATE TABLE Following\n" +
                    "(\n" +
                    "    user_id integer,\n" +
                    "FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,\n" +
                    "    list_id integer,\n" +
                    "FOREIGN KEY (list_id) REFERENCES MimounaList(list_id) ON DELETE CASCADE\n" +
                    ")");
            pstmt6 = connection.prepareStatement("CREATE TABLE Celebrated_in\n" +
                    "(\n" +
                    "    mimouna_id integer,\n" +
                    "FOREIGN KEY (mimouna_id) REFERENCES Mimouna(mimouna_id) ON DELETE CASCADE,\n" +
                    "    list_id integer,\n" +
                    "FOREIGN KEY (list_id) REFERENCES MimounaList(list_id) ON DELETE CASCADE\n" +
                    ")");



            pstmt7 = connection.prepareStatement(
                    "CREATE VIEW TotalListGuests AS " +
                            "SELECT C.list_id, SUM(M.guests_counter) AS total " +
                            "FROM Celebrated_in C, Mimouna M " +
                            "WHERE C.mimouna_id = M.mimouna_id " +
                            "GROUP BY C.list_id " +
                            "ORDER BY C.list_id");

            pstmt8 = connection.prepareStatement(
                    "CREATE VIEW TotalFollowers AS " +
                            "SELECT list_id, COUNT(user_id) AS fTotal " +
                            "FROM Following " +
                            "GROUP BY list_id");


            pstmt9 = connection.prepareStatement(
                    "CREATE VIEW NumOfMimounas AS " +
                            "SELECT C.list_id, COUNT(C.mimouna_id) AS num " +
                            "FROM Celebrated_in C " +
                            "GROUP BY C.list_id " +
                            "ORDER BY C.list_id");

            pstmt10 = connection.prepareStatement(
                    "CREATE VIEW ListRatingHelp AS " +
                            "SELECT TotalListGuests.list_id, TotalListGuests.total AS total, NumOfMimounas.num AS num " +
                            "FROM TotalListGuests " +
                            "INNER JOIN NumOfMimounas ON TotalListGuests.list_id=NumOfMimounas.list_id");

            pstmt15 = connection.prepareStatement(
                    "CREATE VIEW ListRating AS " +
                            "SELECT list_id, (total+num) AS rating " +
                            "FROM ListRatingHelp " +
                            "ORDER BY rating DESC");

            pstmt11 = connection.prepareStatement(
                    "CREATE VIEW SameLists AS " +
                            "SELECT F1.user_id AS u_id1, F1.list_id AS l_id1, F2.user_id AS u_id2, F2.list_id AS l_id2 " +
                            "FROM Following F1, Following F2 " +
                            "WHERE F1.user_id != F2.user_id AND F1.list_id = F2.list_id " +
                            "ORDER BY F1.user_id");

            pstmt12 = connection.prepareStatement(
                    "CREATE VIEW MimounasWithPolitician AS " +
                            "SELECT mimouna_id " +
                            "FROM Mimouna " +
                            "WHERE is_politician_coming = TRUE");

            pstmt13 = connection.prepareStatement(
                    "CREATE VIEW ListsWithPolitician AS " +
                            "SELECT DISTINCT list_id " +
                            "FROM Celebrated_in " +
                            "WHERE mimouna_id IN (SELECT * FROM MimounasWithPolitician) " +
                            "ORDER BY list_id");

            pstmt14 = connection.prepareStatement(
                    "CREATE VIEW AllPoliticians AS " +
                            "SELECT user_id " +
                            "FROM Users " +
                            "WHERE politician = TRUE");


            pstmt1.execute();
            pstmt2.execute();
            pstmt3.execute();
            pstmt4.execute();
            pstmt5.execute();
            pstmt6.execute();
            pstmt7.execute();
            pstmt8.execute();
            pstmt9.execute();
            pstmt10.execute();
            pstmt15.execute();
            pstmt11.execute();
            pstmt12.execute();
            pstmt13.execute();
            pstmt14.execute();

            } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt1.close();
                pstmt2.close();
                pstmt3.close();
                pstmt4.close();
                pstmt5.close();
                pstmt6.close();
                pstmt7.close();
                pstmt8.close();
                pstmt9.close();
                pstmt10.close();
                pstmt15.close();
                pstmt11.close();
                pstmt12.close();
                pstmt13.close();
                pstmt14.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }

    }

    @SuppressWarnings("Duplicates")
    public static void clearTables() {

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt1, pstmt2, pstmt3,pstmt4, pstmt5, pstmt6;
        pstmt1 = null;
        pstmt2 = null;
        pstmt3 = null;
        pstmt4 = null;
        pstmt5 = null;
        pstmt6 = null;
        try {

            pstmt1 = connection.prepareStatement("DELETE FROM Users");
            pstmt2 = connection.prepareStatement("DELETE FROM Mimouna");
            pstmt3 = connection.prepareStatement("DELETE FROM MimounaList");
            pstmt4 = connection.prepareStatement("DELETE FROM Attending");
            pstmt5 = connection.prepareStatement("DELETE FROM Following");
            pstmt6 = connection.prepareStatement("DELETE FROM Celebrated_in");

            pstmt1.executeUpdate();
            pstmt2.executeUpdate();
            pstmt3.executeUpdate();
            pstmt4.executeUpdate();
            pstmt5.executeUpdate();
            pstmt6.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt1.close();
                pstmt2.close();
                pstmt3.close();
                pstmt4.close();
                pstmt5.close();
                pstmt6.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public static void dropTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt1, pstmt2, pstmt3,pstmt4, pstmt5, pstmt6,pstmt7,pstmt8,pstmt9,pstmt10,pstmt11;
        PreparedStatement pstmt12,pstmt13,pstmt14,pstmt15;
        pstmt1 = null;
        pstmt2 = null;
        pstmt3 = null;
        pstmt4 = null;
        pstmt5 = null;
        pstmt6 = null;
        pstmt7 = null;
        pstmt8 = null;
        pstmt15 = null;
        pstmt9 = null;
        pstmt10 = null;
        pstmt11 = null;
        pstmt12 = null;
        pstmt13 = null;
        pstmt14 = null;
        try {

            pstmt1 = connection.prepareStatement("DROP TABLE IF EXISTS Users");
            pstmt2 = connection.prepareStatement("DROP TABLE IF EXISTS Mimouna\n");
            pstmt3 = connection.prepareStatement("DROP TABLE IF EXISTS MimounaList\n");
            pstmt4 = connection.prepareStatement("DROP TABLE IF EXISTS Attending\n");
            pstmt5 = connection.prepareStatement("DROP TABLE IF EXISTS Following\n");
            pstmt6 = connection.prepareStatement("DROP TABLE IF EXISTS Celebrated_in\n");
            pstmt7 = connection.prepareStatement("DROP VIEW IF EXISTS TotalListGuests\n");
            pstmt8 = connection.prepareStatement("DROP VIEW IF EXISTS NumOfMimounas\n");
            pstmt9 = connection.prepareStatement("DROP VIEW IF EXISTS ListRatingHelp\n");
            pstmt15 = connection.prepareStatement("DROP VIEW IF EXISTS ListRating\n");
            pstmt10 = connection.prepareStatement("DROP VIEW IF EXISTS SameLists\n");
            pstmt11 = connection.prepareStatement("DROP VIEW IF EXISTS TotalFollowers\n");
            pstmt12 = connection.prepareStatement("DROP VIEW IF EXISTS MimounasWithPolitician\n");
            pstmt13 = connection.prepareStatement("DROP VIEW IF EXISTS ListsWithPolitician\n");
            pstmt14 = connection.prepareStatement("DROP VIEW IF EXISTS AllPoliticians\n");

            pstmt13.execute();
            pstmt12.execute();
            pstmt14.execute();
            pstmt15.execute();
            pstmt9.execute();
            pstmt7.execute();
            pstmt8.execute();
            pstmt10.execute();
            pstmt11.execute();
            pstmt4.execute();
            pstmt5.execute();
            pstmt6.execute();
            pstmt1.execute();
            pstmt2.execute();
            pstmt3.execute();

        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt1.close();
                pstmt2.close();
                pstmt3.close();
                pstmt4.close();
                pstmt5.close();
                pstmt6.close();
                pstmt7.close();
                pstmt8.close();
                pstmt15.close();
                pstmt9.close();
                pstmt10.close();
                pstmt11.close();
                pstmt12.close();
                pstmt13.close();
                pstmt14.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ReturnValue addUser(User user) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Users" +
                    " VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getCity());
            pstmt.setBoolean(4, user.getPolitician());

            pstmt.execute();
            return OK;
        } catch (SQLException e) {
            String state = e.getSQLState();
            if(state.equals("23502") || state.equals("23514")){          //Not Null Violation || Check Violation
                return BAD_PARAMS;
            }
            if(state.equals("23505")){                                      //Unique Violation
                return ALREADY_EXISTS;
            }
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
        }


    }

    public static User getUserProfile(Integer userId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        User Uresult = User.badUser();
        try {
            pstmt = connection.prepareStatement("SELECT * FROM Users WHERE user_id='" + userId + "'");
            ResultSet results = pstmt.executeQuery();
            if(results.isBeforeFirst() == false) {
                return Uresult;
            }
            results.next();
            Uresult.setId(results.getInt("user_id"));
            Uresult.setName(results.getString("user_name"));
            Uresult.setCity(results.getString("city"));
            Uresult.setPolitician(results.getBoolean("politician"));
            if(results != null){
                results.close();
            }
            return Uresult;
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return Uresult;
    }

    public static ReturnValue deleteUser(User user) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM Users " +

                            "WHERE user_id = ?");
            pstmt.setInt(1,user.getId());

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0){
                return NOT_EXISTS;
            }
            return OK;
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
        }

    }

    public static ReturnValue addMimouna(Mimouna mimouna) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Mimouna" +
                    " VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, mimouna.getId());
            pstmt.setString(2, mimouna.getUserName());
            pstmt.setString(3, mimouna.getFamilyName());
            pstmt.setString(4, mimouna.getCity());
            pstmt.setInt(5, 0);                     //guests counter init to 0
            pstmt.setBoolean(6, false);             //is politician coming init to 0


            pstmt.execute();
            return OK;
        } catch (SQLException e) {
            String state = e.getSQLState();
            if(state.equals("23502") || state.equals("23514")){          //Not Null Violation || Check Violation
                return BAD_PARAMS;
            }
            if(state.equals("23505")){                                     //Unique Violation
                return ALREADY_EXISTS;
            }
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
        }


    }

    public static Mimouna getMimouna(Integer mimounaId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        Mimouna Uresult = Mimouna.badMimouna();
        try {
            pstmt = connection.prepareStatement("SELECT * FROM Mimouna WHERE mimouna_id='" + mimounaId + "'");
            ResultSet results = pstmt.executeQuery();
            if(results.isBeforeFirst() == false) {
                return Uresult;
            }
            results.next();
            Uresult.setId(results.getInt("mimouna_id"));
            Uresult.setUserName(results.getString("user_name"));
            Uresult.setFamilyName(results.getString("family_name"));
            Uresult.setCity(results.getString("city"));
            Uresult.setGuestCount(results.getInt("guests_counter"));
            Uresult.setPoliticianComing(results.getBoolean("is_politician_coming"));
            if(results != null){
                results.close();
            }
            return Uresult;
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return Uresult;
    }

    public static ReturnValue deleteMimouna(Mimouna mimouna) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM Mimouna " +

                            "where mimouna_id = ?");
            pstmt.setInt(1,mimouna.getId());

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0){
                return NOT_EXISTS;
            }
            return OK;
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
        }

    }

    public static ReturnValue addMimounalist(MimounaList mimounaList) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO MimounaList" +
                    " VALUES (?, ?)");
            pstmt.setInt(1, mimounaList.getId());
            pstmt.setString(2, mimounaList.getCity());


            pstmt.execute();
            return OK;
        } catch (SQLException e) {
            String state = e.getSQLState();
            if(state.equals("23502") || state.equals("23514")){          //Not Null Violation || Check Violation
                return BAD_PARAMS;
            }
            if(state.equals("23505")){                                     //Unique Violation
                return ALREADY_EXISTS;
            }
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
        }


    }

    public static MimounaList getMimounalist(Integer mimounalistId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        MimounaList Uresult = MimounaList.badMimounalist();
        try {
            pstmt = connection.prepareStatement("SELECT * FROM MimounaList WHERE list_id='" + mimounalistId + "'");
            ResultSet results = pstmt.executeQuery();
            if(results.isBeforeFirst() == false) {
                return Uresult;
            }
            results.next();
            Uresult.setId(results.getInt("list_id"));
            Uresult.setCity(results.getString("city"));
            if(results != null){
                results.close();
            }
            return Uresult;
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return Uresult;
    }

    public static ReturnValue deleteMimounalist(MimounaList mimounalist) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM MimounaList " +

                            "where list_id = ?");
            pstmt.setInt(1,mimounalist.getId());

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0){
                return NOT_EXISTS;
            }
            return OK;
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return ERROR;
                //e.printStackTrace()();
            }
        }
    }

    public static ReturnValue attendMimouna(Integer mimounaId, Integer guests){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            Mimouna m = getMimouna(mimounaId);

            int new_counter = m.getGuestCount() + guests;
            pstmt = connection.prepareStatement(
                    "UPDATE Mimouna " +
                            "SET guests_counter = ? " +
                            "where mimouna_id = ?");
            pstmt.setInt(1,new_counter);
            pstmt.setInt(2, mimounaId);
            if( m.getId() == -1){
                return NOT_EXISTS;
            }
            if( new_counter < 0){
                return BAD_PARAMS;
            }
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0){
                return ERROR;
            }
            return OK;
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }

    }

    public static ReturnValue confirmAttendancePoliticianToMimouna(Integer mimounaId, Integer userId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt,pstmt2;
        pstmt = null;
        pstmt2 = null;
        try {
        Mimouna m = getMimouna(mimounaId);
        User u = getUserProfile(userId);

            if(m.getId() == -1 || u.getId() == -1){
                return NOT_EXISTS;
            }
            if(!(u.getPolitician())){
                return BAD_PARAMS;

            }
            if(m.getIsPoliticianComing()){
                return OK;
            }
            pstmt2 = connection.prepareStatement("INSERT INTO Attending" +
                    " VALUES (?, ?)");
            pstmt2.setInt(1, userId);
            pstmt2.setInt(2, mimounaId);


            pstmt = connection.prepareStatement(
                    "UPDATE Mimouna " +
                            "SET is_politician_coming = ? " +
                            "where mimouna_id = ?");
            pstmt.setBoolean(1,true);
            pstmt.setInt(2, mimounaId);


            pstmt2.execute();
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0){
                return ERROR;
            }
            return OK;
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null){
                    pstmt.close();
                }
                if(pstmt2 != null){
                    pstmt2.close();
                }

            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }

    }

    public static ReturnValue addMimounaToMimounalist(Integer mimounaId, Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt2,pstmt3;
        pstmt2 = null;
        pstmt3 = null;
        ResultSet results = null;
        try {
            Mimouna m = getMimouna(mimounaId);
            MimounaList l = getMimounalist(mimounalistId);


            pstmt2 = connection.prepareStatement("INSERT INTO Celebrated_in" +
                    " VALUES (?, ?)");
            pstmt2.setInt(1, mimounaId);
            pstmt2.setInt(2, mimounalistId);
            if(m.getId() == -1 || l.getId() == -1 || m.getCity() == null || !(m.getCity().equals(l.getCity())) ){
                return BAD_PARAMS;
            }
            pstmt3 = connection.prepareStatement("SELECT list_id, mimouna_id FROM Celebrated_in" +
                    " WHERE mimouna_id = ? AND list_id = ?");
            pstmt3.setInt(1, mimounaId);
            pstmt3.setInt(2, mimounalistId);

            results = pstmt3.executeQuery();
            if(results.next() == true) {
                return ALREADY_EXISTS;
            }

            pstmt2.execute();
            return OK;
        } catch (SQLException e) {
            String state = e.getSQLState();
            if(state.equals("23503") || state.equals("23505")){          //Foreign key violation || Unique Violation
                return ALREADY_EXISTS;
            }
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt2 != null) pstmt2.close();
                if(pstmt3 != null) pstmt3.close();
                if(results != null) results.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ReturnValue removeMimounaFromMimounalist(Integer mimounaId, Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt;
        pstmt = null;
        try {
            Mimouna m = getMimouna(mimounaId);
            MimounaList l = getMimounalist(mimounalistId);
            if(m.getId() == -1 || l.getId() == -1){
                return NOT_EXISTS;
            }

            pstmt = connection.prepareStatement(
                    "DELETE FROM Celebrated_in " +
                            "WHERE mimouna_id = ? AND list_id=?");
            pstmt.setInt(1, mimounaId);
            pstmt.setInt(2, mimounalistId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return NOT_EXISTS;
            }
            return OK;

        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ReturnValue followMimounalist(Integer userId, Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt,pstmt2;
        pstmt = null;
        pstmt2 = null;
        ResultSet results = null;
        try {
            User u = getUserProfile(userId);
            MimounaList l = getMimounalist(mimounalistId);


            pstmt2 = connection.prepareStatement(
                    "SELECT * " +
                            "FROM Following " +
                            "WHERE user_id = ? AND list_id=?");
            pstmt2.setInt(1, userId);
            pstmt2.setInt(2, mimounalistId);



            pstmt = connection.prepareStatement("INSERT INTO Following" +
                    " VALUES (?, ?)");
            pstmt.setInt(1, userId);
            pstmt.setInt(2, mimounalistId);

            if(u.getId() == -1 || l.getId() == -1){
                return NOT_EXISTS;
            }
            results = pstmt2.executeQuery();
            if(results.next() == true) {
                return ALREADY_EXISTS;
            }
            pstmt.execute();


            return OK;
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
                if(pstmt2 != null) pstmt2.close();
                if(results != null){
                    results.close();
                }
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ReturnValue stopFollowMimounalist(Integer userId, Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt;
        pstmt = null;
        try {
            User u = getUserProfile(userId);
            MimounaList l = getMimounalist(mimounalistId);
            if(u.getId() == -1 || l.getId() == -1){
                return NOT_EXISTS;
            }

            pstmt = connection.prepareStatement(
                    "DELETE FROM Following " +
                            "WHERE user_id = ? AND list_id=?");
            pstmt.setInt(1, userId);
            pstmt.setInt(2, mimounalistId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return NOT_EXISTS;
            }

            return OK;
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static Integer getMimounalistTotalGuests(Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt2;
        pstmt2 = null;
        ResultSet results = null;
        try {
            MimounaList l = getMimounalist(mimounalistId);




            pstmt2 = connection.prepareStatement(
                    "SELECT total " +
                            "FROM TotalListGuests " +
                            "WHERE list_id = ?");
            pstmt2.setInt(1, mimounalistId);
            if(l.getId() == -1){
                return 0;
            }
            results = pstmt2.executeQuery();
            if(results.isBeforeFirst() == false) {
                return 0;
            }
            results.next();
            return results.getInt(1);

        } catch (SQLException e) {
            return 0;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt2 != null) pstmt2.close();
                if(results != null){
                    results.close();
                }

            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }

    }

    public static Integer getMimounalistFollowersCount(Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt;
        pstmt = null;
        ResultSet results = null;
        try {
            MimounaList l = getMimounalist(mimounalistId);
            if(l.getId() == -1){
                return 0;
            }



            pstmt = connection.prepareStatement(
                    "SELECT fTotal " +
                            "FROM TotalFollowers " +
                            "WHERE list_id = ?");
            pstmt.setInt(1, mimounalistId);
            results = pstmt.executeQuery();
            if(results.isBeforeFirst() == false) {
                return 0;
            }
            results.next();
            return results.getInt(1);


        } catch (SQLException e) {
            return 0;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt != null) pstmt.close();
                if(results != null){
                    results.close();
                }
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static String getMostKnownMimouna(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt,pstmt2,pstmt3,pstmt4,pstmt5,pstmt6,pstmt7,pstmt8;
        pstmt = null;
        pstmt2 = null;
        pstmt3 = null;
        pstmt4 = null;
        pstmt5 = null;
        pstmt6 = null;
        pstmt7 = null;
        pstmt8 = null;
        ResultSet results = null;
        try {
            pstmt = connection.prepareStatement(
                    "SELECT COUNT(*) " +
                            "FROM Celebrated_in ");
            results = pstmt.executeQuery();
            if(results.isBeforeFirst() == false) {
                return null;
            }
            results.next();
            if(results.getInt(1) == 0){
                return "";
            }

            pstmt2 = connection.prepareStatement(
                    "CREATE VIEW MimounasInLists AS " +
                            "(SELECT mimouna_id, COUNT(list_id) AS num FROM Celebrated_in GROUP BY mimouna_id ORDER BY mimouna_id DESC) ");
            pstmt2.execute();
            pstmt4 = connection.prepareStatement(
                    "CREATE VIEW Maxmimounas AS " +
                    "SELECT mimouna_id, num " +
                            "FROM MimounasInLists WHERE num = (SELECT MAX(num) FROM MimounasInLists) ");
            pstmt4.execute();

            pstmt5 = connection.prepareStatement(
                            "SELECT mimouna_id, num " +
                            "FROM Maxmimounas WHERE mimouna_id = (SELECT MAX(mimouna_id) FROM Maxmimounas)");

            results = pstmt5.executeQuery();
            if(results.isBeforeFirst() == false) {
                return null;
            }
            results.next();
            int mimounaID = results.getInt(1);
            int num = results.getInt(2);

            if(num == 0){
                pstmt6 = connection.prepareStatement(
                        "SELECT MAX(mimouna_id) " +
                                "FROM Mimouna ");

                results = pstmt6.executeQuery();
                if(results.isBeforeFirst() == false) {
                    return null;
                }
                results.next();
                mimounaID = results.getInt(1);
            }
            pstmt3 = connection.prepareStatement(
                    "SELECT family_name " +
                            "FROM Mimouna " +
                            "WHERE mimouna_id = ?");
            pstmt3.setInt(1, mimounaID);
            results = pstmt3.executeQuery();
            if(results.isBeforeFirst() == false) {
                return null;
            }
            results.next();
            return results.getString(1);


        } catch (SQLException e) {
            return null;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt7 = connection.prepareStatement("DROP VIEW IF EXISTS MimounasInLists\n");
                pstmt8 = connection.prepareStatement("DROP VIEW IF EXISTS Maxmimounas\n");
                pstmt8.execute();
                pstmt7.execute();
                if(pstmt7 != null) pstmt7.close();
                if(pstmt8 != null) pstmt8.close();
                if(pstmt != null) pstmt.close();
                if(pstmt2 != null) pstmt2.close();
                if(pstmt3 != null) pstmt3.close();
                if(pstmt4 != null) pstmt4.close();
                if(pstmt5 != null) pstmt5.close();
                if(pstmt6 != null) pstmt6.close();
                if(results != null){
                    results.close();
                }
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }

    }

    public static Integer getMostPopularMimounalist(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt,pstmt2,pstmt3,pstmt4, pstmt5;
        pstmt = null;
        pstmt2 = null;
        pstmt3 = null;
        pstmt4 = null;
        pstmt5 = null;
        ResultSet results = null;
        try {
            pstmt5 = connection.prepareStatement(
                    "SELECT COUNT(*) AS RowCnt\n" +
                            "FROM TotalListGuests ");
            results = pstmt5.executeQuery();
            if(results.isBeforeFirst() == false) {
                return 0;
            }
            results.next();
            if(results.getInt(1) == 0){
                pstmt3 = connection.prepareStatement(
                        "SELECT MAX(list_id) " +
                                "FROM MimounaList ");
                results = pstmt3.executeQuery();
                if(results.isBeforeFirst() == false) {
                    return 0;
                }

                results.next();
                return results.getInt(1);
            }
            pstmt = connection.prepareStatement(
                            "CREATE VIEW RelevantLists AS " +
                            "SELECT list_id, total FROM TotalListGuests WHERE total = (SELECT MAX(total) FROM TotalListGuests)");
            pstmt.execute();


            pstmt2 = connection.prepareStatement(
                    "SELECT list_id, total FROM TotalListGuests WHERE list_id = (SELECT MAX(list_id) " +
                            "FROM RelevantLists) ");
            results = pstmt2.executeQuery();
            if(results.isBeforeFirst() == false) {
                return 0;
            }
            results.next();
            int t = results.getInt(2);
            if( t == 0 ){
                pstmt3 = connection.prepareStatement(
                        "SELECT MAX(list_id) " +
                                "FROM MimounaList ");
                results = pstmt3.executeQuery();
                if(results.isBeforeFirst() == false) {
                    return 0;
                }

                results.next();
                return results.getInt(1);
            }
            return results.getInt(1);
        } catch (SQLException e) {
            return 0;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt4 = connection.prepareStatement("DROP VIEW IF EXISTS RelevantLists\n");
                pstmt4.execute();
                if(pstmt4 != null) {
                    pstmt4.close();
                }
                if(pstmt != null) {
                    pstmt.close();
                }
                if(pstmt2 != null) {
                    pstmt2.close();
                }
                if(pstmt3 != null) {
                    pstmt3.close();
                }
                if(results != null){
                    results.close();
                }
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ArrayList<Integer> getMostRatedMimounaList(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt3;
        pstmt3 = null;
        ResultSet results = null;
        ArrayList<Integer> IDs = new ArrayList<>();
        try {





            pstmt3 = connection.prepareStatement(
                    "SELECT list_id " +
                            "FROM ListRating " +
                            "LIMIT 10 ");
            results = pstmt3.executeQuery();
            if(results.isBeforeFirst() == false) {
                IDs = new ArrayList<>();
                return IDs;
            }
            results.next();

            while(results.isAfterLast() != true){
                IDs.add(results.getInt(1));
                results.next();
            }

            return IDs;
        } catch (SQLException e) {
            IDs = new ArrayList<>();
            return IDs;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt3 != null) pstmt3.close();
                if(results != null){
                    results.close();
                }
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ArrayList<Integer> getCloseUsers(Integer userId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt2,pstmt3;
        pstmt2 = null;
        pstmt3 = null;
        ResultSet results = null;
        ArrayList<Integer> IDs = new ArrayList<>();
        try {
            pstmt3 = connection.prepareStatement(
                            "SELECT COUNT(list_id) FROM Following WHERE user_id = ?");
            pstmt3.setInt(1, userId);
            results = pstmt3.executeQuery();
            if(results.isBeforeFirst() == false) {
                IDs = new ArrayList<>();
                return IDs;
            }
            results.next();
            if(results.getInt(1) == 0){
                IDs = new ArrayList<>();
                return IDs;
            }

            pstmt2 = connection.prepareStatement(
                    "SELECT user_id AS target FROM Users " +
                            "GROUP BY user_id " +
                            "HAVING((SELECT COUNT(l_id1) FROM (SELECT * FROM SameLists WHERE u_id2 = ? AND u_id1 = user_id) AS numofsame)*100 / " +
                            "(SELECT COUNT(list_id) FROM Following WHERE user_id = ?) >= 67) " +
                            "ORDER BY user_id " +
                            "LIMIT 10");
            pstmt2.setInt(1, userId);
            pstmt2.setInt(2, userId);
            results = pstmt2.executeQuery();

            if(results.isBeforeFirst() == false) {
                IDs = new ArrayList<>();
                return IDs;
            }
            results.next();

            while(results.isAfterLast() != true){
                IDs.add(results.getInt(1));
                results.next();
            }

            return IDs;
        } catch (SQLException e) {
            IDs = new ArrayList<>();
            return IDs;
            //e.printStackTrace()();
        }
        finally {
            try {
                if(pstmt2 != null) pstmt2.close();
                if(pstmt3 != null) pstmt3.close();
                if(results != null){
                    results.close();
                }
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ArrayList<Integer> getMimounaListRecommendation (Integer userId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt2,pstmt3,pstmt4,pstmt5,pstmt6,pstmt7,pstmt8,pstmt9,pstmt10,pstmt11,pstmt12;
        pstmt2 = null;
        pstmt3 = null;
        pstmt4 = null;
        pstmt5 = null;
        pstmt6 = null;
        pstmt7 = null;
        pstmt8 = null;
        pstmt9 = null;
        pstmt10 = null;
        pstmt11 = null;
        pstmt12 = null;
        ResultSet results = null;
        ArrayList<Integer> IDs = new ArrayList<>();
        try {

            pstmt9 = connection.prepareStatement(
                    "SELECT COUNT(list_id) FROM Following WHERE user_id = ?");
            pstmt9.setInt(1, userId);
            results = pstmt9.executeQuery();
            if(results.isBeforeFirst() == false) {
                IDs = new ArrayList<>();
                return IDs;
            }
            results.next();
            if(results.getInt(1) == 0){
                IDs = new ArrayList<>();
                return IDs;
            }

            pstmt10 = connection.prepareStatement(
                    "SELECT COUNT(*) AS RowCnt\n" +
                            "FROM (SELECT user_id AS target FROM Users " +
                            "GROUP BY user_id " +
                            "HAVING((SELECT COUNT(l_id1) FROM (SELECT * FROM SameLists WHERE u_id2 = '" + userId + "' AND u_id1 = user_id) AS numofsame)*100 / " +
                            "(SELECT COUNT(list_id) FROM Following WHERE user_id = '" + userId + "') > 66)) " +
                            "AS test");
            results = pstmt10.executeQuery();
            if(results.isBeforeFirst() == false) {
                IDs = new ArrayList<>();
                return IDs;
            }
            results.next();
            if(results.getInt(1) == 0){
                IDs = new ArrayList<>();
                return IDs;
            }



            pstmt2 = connection.prepareStatement(
                    "CREATE VIEW FindClose AS " +
                            "SELECT user_id AS target FROM Users " +
                            "GROUP BY user_id " +
                            "HAVING((SELECT COUNT(l_id1) FROM (SELECT * FROM SameLists WHERE u_id2 = '" + userId + "' AND u_id1 = user_id) AS numofsame)*100 / " +
                            "(SELECT COUNT(list_id) FROM Following WHERE user_id = '" + userId + "') >= 67)");
            pstmt2.execute();


            pstmt3 = connection.prepareStatement(
                    "CREATE VIEW FindCloseList AS " +
                            "SELECT DISTINCT F.list_id AS l_id " +
                            "FROM Following F " +
                            "WHERE user_id IN (SELECT * FROM FindClose)");
            pstmt3.execute();

            pstmt4 = connection.prepareStatement(
                    "CREATE VIEW PotentialList AS " +
                            "SELECT l_id AS list_id " +
                            "FROM FindCloseList " +
                            "WHERE l_id NOT IN(SELECT list_id FROM Following WHERE user_id = '" + userId + "') " +
                            "ORDER BY l_id");
            pstmt4.execute();


            pstmt11 = connection.prepareStatement(
                    "CREATE VIEW FollowersFromClose AS " +
                            "SELECT list_id, COUNT(user_id) AS fTotal " +
                            "FROM Following " +
                            "WHERE user_id IN(SELECT * FROM FindClose)" +
                            "GROUP BY list_id");
            pstmt11.execute();

            pstmt5 = connection.prepareStatement(
                    "SELECT list_id FROM FollowersFromClose " +
                            "WHERE list_id IN(SELECT * FROM PotentialList) " +
                            "ORDER BY fTotal DESC, list_id " +
                            "LIMIT 3 ");
            results = pstmt5.executeQuery();
            if(results.isBeforeFirst() == false) {
                IDs = new ArrayList<>();
                return IDs;
            }
            results.next();

            while(results.isAfterLast() != true){
                IDs.add(results.getInt(1));
                results.next();
            }

            return IDs;
        } catch (SQLException e) {
            IDs = new ArrayList<>();
            return IDs;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt6 = connection.prepareStatement("DROP VIEW IF EXISTS FindClose\n");
                pstmt7 = connection.prepareStatement("DROP VIEW IF EXISTS FindCloseList\n");
                pstmt8 = connection.prepareStatement("DROP VIEW IF EXISTS PotentialList\n");
                pstmt12 = connection.prepareStatement("DROP VIEW IF EXISTS FollowersFromClose\n");
                pstmt12.execute();
                pstmt8.execute();
                pstmt7.execute();
                pstmt6.execute();
                if(pstmt9 != null) pstmt9.close();
                if(pstmt10 != null) pstmt10.close();
                if(pstmt6 != null) pstmt6.close();
                if(pstmt7 != null) pstmt7.close();
                if(pstmt8 != null) pstmt8.close();
                if(pstmt2 != null) pstmt2.close();
                if(pstmt3 != null) pstmt3.close();
                if(pstmt4 != null) pstmt4.close();
                if(pstmt5 != null) pstmt5.close();
                if(results != null){
                    results.close();
                }

            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ArrayList<Integer> getTopPoliticianMimounaList(Integer userId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt2,pstmt3,pstmt4,pstmt6,pstmt5;
        pstmt2 = null;
        pstmt3 = null;
        pstmt4 = null;
        pstmt6 = null;
        pstmt5 = null;
        ResultSet results = null;
        ArrayList<Integer> IDs = new ArrayList<>();
        try {

            pstmt5 = connection.prepareStatement(
                    "SELECT COUNT(*) AS RowCnt\n " +
                            "FROM ListsWithPolitician ");
            results = pstmt5.executeQuery();
            if(results.isBeforeFirst() == false) {
                IDs = new ArrayList<>();
                return IDs;
            }
            results.next();
            if(results.getInt(1) == 0){
                IDs = new ArrayList<>();
                return IDs;
            }

            pstmt2 = connection.prepareStatement(
                            "SELECT city  FROM Users " +
                            "WHERE user_id = ? AND politician = FALSE");
            pstmt2.setInt(1, userId);
            results = pstmt2.executeQuery();
            if(results.isBeforeFirst() == false) {
                IDs = new ArrayList<>();
                return IDs;
            }
            //if we reached here, the required city is in results.----------
            results.next();
            String s = results.getString("city");
            pstmt3 = connection.prepareStatement(
                    "CREATE VIEW PotentialLists AS " +
                            "SELECT list_id " +
                            "FROM ListsWithPolitician " +
                            "WHERE list_id IN(SELECT list_id FROM MimounaList WHERE city = '" + s + "')");
            pstmt3.execute();



            pstmt4 = connection.prepareStatement(
                    "SELECT list_id FROM TotalListGuests " +
                            "WHERE list_id IN(SELECT * FROM PotentialLists) " +
                            "ORDER BY total DESC, list_id " +
                            "LIMIT 10 ");
            results = pstmt4.executeQuery();
            if(results.isBeforeFirst() == false) {
                IDs = new ArrayList<>();
                return IDs;
            }
            results.next();

            while(results.isAfterLast() != true){
                IDs.add(results.getInt(1));
                results.next();
            }

            return IDs;
        } catch (SQLException e) {
            IDs = new ArrayList<>();
            return IDs;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt6 = connection.prepareStatement("DROP VIEW IF EXISTS PotentialLists\n");
                pstmt6.execute();
                if(pstmt6 != null) pstmt6.close();
                if(pstmt5 != null) pstmt5.close();
                if(pstmt2 != null) pstmt2.close();
                if(pstmt3 != null) pstmt3.close();
                if(pstmt4 != null) pstmt4.close();
                if(results != null){
                    results.close();
                }

            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }
}

