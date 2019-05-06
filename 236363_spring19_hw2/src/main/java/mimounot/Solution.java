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

                            "where user_id = ?");
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
        return null;
    }

    public static ReturnValue confirmAttendancePoliticianToMimouna(Integer mimounaId, Integer userId){
        return null;
    }

    public static ReturnValue addMimounaToMimounalist(Integer mimounaId, Integer mimounalistId){
        return null;
    }

    public static ReturnValue removeMimounaFromMimounalist(Integer mimounaId, Integer mimounalistId){
        return null;
    }

    public static ReturnValue followMimounalist(Integer userId, Integer mimounalistId){
        return null;
    }

    public static ReturnValue stopFollowMimounalist(Integer userId, Integer mimounalistId){
        return null;
    }

    public static Integer getMimounalistTotalGuests(Integer mimounalistId){
        return null;
    }

    public static Integer getMimounalistFollowersCount(Integer mimounalistId){
        return null;
    }

    public static String getMostKnownMimouna(){
        return null;
    }

    public static Integer getMostPopularMimounalist(){
        return null;
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

