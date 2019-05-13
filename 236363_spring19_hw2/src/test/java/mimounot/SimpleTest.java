package mimounot;


import mimounot.business.ReturnValue;
import mimounot.business.User;
import org.junit.Test;
import static mimounot.business.ReturnValue.*;
import static org.junit.Assert.assertEquals;



public class SimpleTest extends AbstractTest{

    @Test
    public void simpleTestCreateUser()
    {
        User v = new User();
        v.setId(1);
        v.setName("Dani");
        v.setCity("");
        v.setPolitician(true);
        ReturnValue ret = Solution.addUser(v);
        assertEquals(OK, ret);
    }

    @Test
    public void testDeleteUser(){
        User u = new User();
        u.setId(10);
        u.setName("Eli");
        u.setCity("Kfar Saba");
        u.setPolitician(true);

        ReturnValue ret = Solution.deleteUser(u);
        assertEquals(NOT_EXISTS , ret);

    }
}