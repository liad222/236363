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
        PreparedStatement pstmt1, pstmt2, pstmt3,pstmt4, pstmt5, pstmt6;
        pstmt1 = null;
        pstmt2 = null;
        pstmt3 = null;
        pstmt4 = null;
        pstmt5 = null;
        pstmt6 = null;
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
                    "    city text,\n" +
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

            pstmt1.execute();
            pstmt2.execute();
            pstmt3.execute();
            pstmt4.execute();
            pstmt5.execute();
            pstmt6.execute();



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

            pstmt1 = connection.prepareStatement("DELETE * FROM Users");
            pstmt2 = connection.prepareStatement("DELETE * FROM Mimouna");
            pstmt3 = connection.prepareStatement("DELETE * FROM MimounaList");
            pstmt4 = connection.prepareStatement("DELETE * FROM Attending");
            pstmt5 = connection.prepareStatement("DELETE * FROM Following");
            pstmt6 = connection.prepareStatement("DELETE * FROM Celebrated_in");

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
        PreparedStatement pstmt1, pstmt2, pstmt3,pstmt4, pstmt5, pstmt6;
        pstmt1 = null;
        pstmt2 = null;
        pstmt3 = null;
        pstmt4 = null;
        pstmt5 = null;
        pstmt6 = null;
        try {

            pstmt1 = connection.prepareStatement("DROP TABLE IF EXISTS Users");
            pstmt2 = connection.prepareStatement("DROP TABLE IF EXISTS Mimouna\n");
            pstmt3 = connection.prepareStatement("DROP TABLE IF EXISTS MimounaList\n");
            pstmt4 = connection.prepareStatement("DROP TABLE IF EXISTS Attending\n");
            pstmt5 = connection.prepareStatement("DROP TABLE IF EXISTS Following\n");
            pstmt6 = connection.prepareStatement("DROP TABLE IF EXISTS Celebrated_in\n");

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
                pstmt.close();
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

        return OK;
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
            results.close();

        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
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
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
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
        return OK;
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
                pstmt.close();
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

        return OK;
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
            results.close();

        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
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
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
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
        return OK;
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
                pstmt.close();
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

        return OK;
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
            results.close();

        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
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
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
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
        return OK;
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
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
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
        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
                pstmt2.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static ReturnValue addMimounaToMimounalist(Integer mimounaId, Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt2;
        pstmt2 = null;
        try {
            Mimouna m = getMimouna(mimounaId);
            MimounaList l = getMimounalist(mimounalistId);
            if(m.getId() == -1 || l.getId() == -1 || m.getCity() == null || m.getCity() != l.getCity()){
                return BAD_PARAMS;
            }

            pstmt2 = connection.prepareStatement("INSERT INTO Celebrated_in" +
                    " VALUES (?, ?)");
            pstmt2.setInt(1, mimounaId);
            pstmt2.setInt(2, mimounalistId);

            pstmt2.execute();

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
                pstmt2.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
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


        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
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



        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
                pstmt2.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
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


        } catch (SQLException e) {
            return ERROR;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static Integer getMimounalistTotalGuests(Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt,pstmt2,pstmt3;
        pstmt = null;
        pstmt2 = null;
        pstmt3 = null;
        ResultSet results = null;
        try {
            MimounaList l = getMimounalist(mimounalistId);
            if(l.getId() == -1){
                return 0;
            }

            pstmt = connection.prepareStatement(
                    "CREATE VIEW [MimounasInList] AS " +
                            "SELECT mimouna_id " +
                            "FROM Celebrated_in " +
                            "WHERE list_id = ?");
            pstmt.setInt(1, mimounalistId);
            pstmt.execute();

            pstmt2 = connection.prepareStatement(
                    "SELECT SUM(guests_counter) " +
                            "FROM Mimouna " +
                            "WHERE mimouna_id IN " +
                            "(SELECT * FROM [MimounasInList])");
            results = pstmt2.executeQuery();
            if(results.isBeforeFirst() == false) {
                return 0;
            }
            results.next();

            pstmt3 = connection.prepareStatement(
                    "DROP VIEW [MimounasInList] ");
            pstmt3.execute();


        } catch (SQLException e) {
            return 0;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
                pstmt2.close();
                pstmt3.close();
                results.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        try {
            return results.getInt(1);
        }catch (Exception e){
            return 0;
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
                    "SELECT COUNT(user_id) " +
                            "FROM Following " +
                            "WHERE list_id = ?");
            pstmt.setInt(1, mimounalistId);
            results = pstmt.executeQuery();
            if(results.isBeforeFirst() == false) {
                return 0;
            }
            results.next();



        } catch (SQLException e) {
            return 0;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
                results.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        try {
            return results.getInt(1);
        }catch (Exception e){
            return 0;
        }
    }

    public static String getMostKnownMimouna(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt,pstmt2,pstmt3;
        pstmt = null;
        pstmt2 = null;
        pstmt3 = null;
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
                    "SELECT mimouna_id,MAX(num) FROM " +
                            "(SELECT mimouna_id, COUNT(list_id) AS num FROM Celebrated_in GROUP BY mimouna_id ORDER BY mimouna_id DESC) ");
            results = pstmt2.executeQuery();
            if(results.isBeforeFirst() == false) {
                return null;
            }
            results.next();
            int mimounaID = results.getInt(1);

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

        } catch (SQLException e) {
            return null;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
                pstmt2.close();
                pstmt3.close();
                results.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        try {
            return results.getString(1);
        }catch (Exception e){
            return null;
        }
    }

    public static Integer getMostPopularMimounalist(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt,pstmt2,pstmt3;
        pstmt = null;
        pstmt2 = null;
        pstmt3 = null;
        ResultSet results = null;
        int max_guests, max_ID;
        max_guests = 0;
        max_ID = 0;
        try {
            pstmt = connection.prepareStatement(
                            "SELECT list_id " +
                            "FROM MimounaList " +
                            "ORDER BY list_id ");
            results = pstmt2.executeQuery();
            if(results.isBeforeFirst() == false) {
                return 0;
            }


            while(results.next()){
                int current_num = getMimounalistTotalGuests(results.getInt("list_id"));
                int current_id = results.getInt("list_id");
                if( current_num > max_guests){
                    max_ID = current_id;
                    max_guests = current_num;
                }
                if( current_num == max_guests && current_id > max_ID){
                    max_ID = current_id;
                }
            }

        } catch (SQLException e) {
            return 0;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
                pstmt2.close();
                pstmt3.close();
                results.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        try {
            return max_ID;
        }catch (Exception e){
            return 0;
        }
    }

    public static ArrayList<Integer> getMostRatedMimounaList(){
        return null;
    }

    public static ArrayList<Integer> getCloseUsers(Integer userId){
        return null;
    }

    public static ArrayList<Integer> getMimounaListRecommendation (Integer userId){
        return null;
    }

    public static ArrayList<Integer> getTopPoliticianMimounaList(Integer userId) {
        return null;
    }
}

