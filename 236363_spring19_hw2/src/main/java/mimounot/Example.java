package mimounot;

import mimounot.business.Mimouna;
import mimounot.business.MimounaList;
import mimounot.business.User;
import mimounot.data.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Example {

    public static void main(String[] args) {

        Solution.clearTables();
        Solution.dropTables();
        Solution.createTables();
        /*
        User livni = new User();
        livni.setId(1);
        livni.setName("liad");
        livni.setCity("Haifa");
        livni.setPolitician(true);

        User omer = new User();
        omer.setId(2);
        omer.setName("ziv");
        omer.setCity("Tel aviv");
        omer.setPolitician(false);

        Solution.addUser(livni);
        Solution.addUser(omer);
        System.out.println(Solution.addUser(livni));
        System.out.println(Solution.getUserProfile(1));
        System.out.println(Solution.getUserProfile(3));
        Solution.deleteUser(livni);
        System.out.println(Solution.deleteUser(livni));
        System.out.println("END OF USER--------------------");



        Mimouna m1 = new Mimouna();
        m1.setId(101);
        m1.setUserName("amir");
        m1.setFamilyName("jurman");
        m1.setCity("Ramat Gan");
        m1.setGuestCount(20);
        m1.setPoliticianComing(false);


        Mimouna m2 = new Mimouna();
        m2.setId(102);
        m2.setUserName("omri");
        m2.setFamilyName("bar");
        m2.setCity("Naharia");
        m2.setGuestCount(0);
        m2.setPoliticianComing(true);

        Solution.addMimouna(m1);
        Solution.addMimouna(m2);
        System.out.println(Solution.addMimouna(m1));
        System.out.println(Solution.getMimouna(101));
        System.out.println(Solution.getMimouna(103));
        Solution.deleteMimouna(m1);
        System.out.println(Solution.deleteMimouna(m1));
        System.out.println("END OF MIMOUNA--------------------");


        MimounaList l1 = new MimounaList();
        l1.setId(1001);
        l1.setCity("Acre");

        MimounaList l2 = new MimounaList();
        l2.setId(1002);
        l2.setCity("Shoham");

        Solution.addMimounalist(l1);
        Solution.addMimounalist(l2);
        System.out.println(Solution.addMimounalist(l1));
        System.out.println(Solution.getMimounalist(1001));
        System.out.println(Solution.getMimounalist(1003));
        Solution.deleteMimounalist(l1);
        System.out.println(Solution.deleteMimounalist(l1));
        System.out.println("END OF MIMOUNALIST--------------------");*/
    }

    private static void deleteFromTable() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM hello_world " +

                            "where id = ?");
            pstmt.setInt(1,1);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("deleted " + affectedRows + " rows");
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
    }

    private static void javaStringExample() {
        System.out.println("in order to concat two strings you can use the + operator, for example:");
        System.out.println("    String hello = \"hello\";\n" +
                "   String world = \" world\";\n" +
                "   System.out.println(hello + world);");
        System.out.println("will yield:");
        String hello = "hello";
        String world = " world";
        System.out.println(hello + world);
        System.out.println("you can concat any object to a string, for example, an integer:");
        System.out.println("    System.out.println(hello + world + 1);");
        System.out.println("will yield:");
        System.out.println(hello + world + 1);
        System.out.println("good luck!");
        System.out.println();
    }

    private static void arrayListExample() {
        System.out.println("in order to create a new arraylist, for example, an arraylist of Integers you need to call");
        System.out.println("    ArrayList<Integer> myArrayList = new ArrayList<>();");
        ArrayList<Integer> myArrayList = new ArrayList<>();
        System.out.println("This is how it looks like: " +myArrayList);
        System.out.println("in order to add an item to the arraylist you need to call the add function");
        System.out.println("    myArrayList.add(1);");
        myArrayList.add(1);
        System.out.println("This is how it looks like: " +myArrayList);
        System.out.println("calling myArrayList.add(2); \nwill yield: ");
        myArrayList.add(2);
        System.out.println(myArrayList);
        System.out.println("please note that arraylist keeps insertion order, and allows duplications");
        System.out.println("calling\n   myArrayList.add(2); \nwill yield: ");
        myArrayList.add(2);
        System.out.println(myArrayList);
        System.out.println("in order to get a value from an array list you need to use the function\n get(int index) ");
        System.out.println("recall that array start with 0 :)");
        System.out.println("    int i = myArrayList.get(0);\nwill yield:");
        int i = myArrayList.get(0);
        System.out.println("    i = " + i );
        System.out.println("good luck!");
        System.out.println();

    }

    private static void updateTable() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "UPDATE hello_world " +
                            "SET short_text = ? " +
                            "where id = ?");
            pstmt.setInt(2,1);
            pstmt.setString(1, "hi world!");
            int affectedRows = pstmt.executeUpdate();
            System.out.println("changed " + affectedRows + " rows");
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
    }

    private static void dropTable()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS hello_world");
            pstmt.execute();
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
    }

    private static void createTable()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            pstmt = connection.prepareStatement("CREATE TABLE hello_world\n" +
                    "(\n" +
                    "    id integer,\n" +
                    "    short_text text ,\n" +
                    "    PRIMARY KEY (id),\n" +
                    "    CHECK (id > 0)\n" +
                    ")");
            pstmt.execute();
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

    }

    private static void insertIntoTable()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO hello_world" +
                    " VALUES (?, ?), (?, ?)");
            pstmt.setInt(1,1);
            pstmt.setString(2, "hello world!");
            pstmt.setInt(3,2);
            pstmt.setString(4,"goodbye world!");


            pstmt.execute();

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
    }

    private static void selectFromTable()
    {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT * FROM hello_world");
            ResultSet results = pstmt.executeQuery();
            DBConnector.printResults(results);
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
    }

}
