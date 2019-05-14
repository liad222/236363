package mimounot;

import mimounot.business.Mimouna;
import mimounot.business.MimounaList;
import mimounot.business.ReturnValue;
import mimounot.business.User;
import org.junit.Test;

import static mimounot.business.ReturnValue.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CRUDTest extends AbstractTest {

    private ReturnValue res;
    @Test
    public void testAddUser() {
        User u = new User();
        u.setId(236363);
        u.setCity("Haifa");
        u.setName("Benny");
        u.setPolitician(false);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.addUser(u);
        assertEquals(ALREADY_EXISTS, res);

        u.setCity("Tel Aviv");
        u.setName("Yossi");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(ALREADY_EXISTS, res);

        u.setCity("Haifa");
        u.setName("Benny");
        u.setPolitician(false);
        u.setId(234123);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        u.setId(0);

        res = Solution.addUser(u);
        assertEquals(BAD_PARAMS, res);

        u.setId(-5);

        res = Solution.addUser(u);
        assertEquals(BAD_PARAMS, res);

        u.setId(236319);
        u.setName(null);

        res = Solution.addUser(u);
        assertEquals(BAD_PARAMS, res);

        u.setName("Yossi");
        u.setCity(null);

        res = Solution.addUser(u);
        assertEquals(BAD_PARAMS, res);

        u.setCity("Jerusalem");

        res = Solution.addUser(u);
        assertEquals(OK, res);
    }

    @Test
    public void testGetUserProfile() {
        res = Solution.addUser(User.badUser());
        assertEquals(BAD_PARAMS, res);

        User res_u;
        res_u = Solution.getUserProfile(236363);
        assertEquals(User.badUser(), res_u);

        User u = new User();
        u.setId(236363);
        u.setCity("Haifa");
        u.setName("Benny");
        u.setPolitician(false);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res_u = Solution.getUserProfile(236363);
        assertEquals(u, res_u);

        u.setCity("Tel Aviv");
        res = Solution.addUser(u);
        assertEquals(ALREADY_EXISTS, res);

        res_u = Solution.getUserProfile(236363);
        assertNotEquals(u, res_u);
        u.setCity("Haifa");
        assertEquals(u, res_u);

        res_u = Solution.getUserProfile(234123);
        assertEquals(User.badUser(), res_u);
    }

    @Test
    public void testDeleteUser() {
        res = Solution.deleteUser(User.badUser());
        assertEquals(NOT_EXISTS, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(false);

        res = Solution.deleteUser(User.badUser());
        assertEquals(NOT_EXISTS, res);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        User res_u = Solution.getUserProfile(u.getId());
        assertEquals(res_u, u);

        res = Solution.deleteUser(u);
        assertEquals(OK, res);

        res_u = Solution.getUserProfile(u.getId());
        assertEquals(res_u, User.badUser());

        res = Solution.deleteUser(u);
        assertEquals(NOT_EXISTS, res);

        res_u = Solution.getUserProfile(u.getId());
        assertEquals(res_u, User.badUser());

        res = Solution.addUser(u);
        assertEquals(OK, res);

        User u2 = new User();
        u2.setId(234123);
        u2.setName("Benny");
        u2.setCity("Haifa");
        u2.setPolitician(false);

        res = Solution.deleteUser(u2);
        assertEquals(NOT_EXISTS, res);

        u.setName("Still Benny");
        u.setCity("Still Haifa");
        u.setPolitician(true);

        res = Solution.deleteUser(u);
        assertEquals(OK, res); // id not changes, so it is indeed the same user
    }

    @Test
    public void testAddMimouna() {
        res = Solution.addMimouna(Mimouna.badMimouna());
        assertEquals(BAD_PARAMS, res);

        Mimouna m = new Mimouna();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addMimouna(m);
        assertEquals(OK, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        m.setUserName("Not Benny");
        m.setFamilyName("Not Kimelfeld");
        m.setCity("Not Haifa");
        m.setGuestCount(499);
        m.setPoliticianComing(false);

        res = Solution.addMimouna(m);
        assertEquals(ALREADY_EXISTS, res);

        m.setId(234123);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addMimouna(m);
        assertEquals(OK, res);

        m.setId(0);

        res = Solution.addMimouna(m);
        assertEquals(BAD_PARAMS, res);

        m.setId(-3);

        res = Solution.addMimouna(m);
        assertEquals(BAD_PARAMS, res);

        m.setId(236319);
        m.setUserName(null);

        res = Solution.addMimouna(m);
        assertEquals(BAD_PARAMS, res);

        m.setUserName("Yossi");
        m.setFamilyName(null);

        res = Solution.addMimouna(m);
        assertEquals(BAD_PARAMS, res);

        m.setFamilyName("Gil");
        m.setCity(null);

        res = Solution.addMimouna(m); // City can't be null according to recent update
        assertEquals(BAD_PARAMS, res);

        m.setId(234218);
        m.setGuestCount(-5);
        m.setCity("Haifa");

        res = Solution.addMimouna(m); // guests = -5 is invalid but add should overwrite that with 0
        assertEquals(OK, res);
    }



    @Test
    public void testGetMimouna() {
        Mimouna res_m = Solution.getMimouna(236363);
        assertEquals(Mimouna.badMimouna(), res_m);

        Mimouna m = new Mimouna();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addMimouna(m);
        assertEquals(OK, res);

        res_m = Solution.getMimouna(236364);
        assertEquals(Mimouna.badMimouna(), res_m);

        res_m = Solution.getMimouna(236363); // according to peleg, non zero guest count gets overwitten
        assertNotEquals(m, res_m);
        m.setPoliticianComing(false);       // same for true politician
        m.setGuestCount(0);
        assertEquals(m, res_m);
    }

    @Test
    public void testDeleteMimouna() {
        res = Solution.deleteMimouna(Mimouna.badMimouna());
        assertEquals(NOT_EXISTS, res);

        Mimouna m = new Mimouna();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.deleteMimouna(m);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addMimouna(m);
        assertEquals(OK, res);

        res = Solution.deleteMimouna(m);
        assertEquals(OK, res);

        res = Solution.deleteMimouna(m);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addMimouna(m);
        assertEquals(OK, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.deleteMimouna(m);
        assertEquals(OK, res);

        res = Solution.deleteUser(u);
        assertEquals(OK, res);

        res = Solution.addMimouna(m);
        assertEquals(OK, res);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.deleteUser(u);
        assertEquals(OK, res);

        res = Solution.deleteMimouna(m);
        assertEquals(OK, res);

        Mimouna res_m = Solution.getMimouna(m.getId());
        assertEquals(Mimouna.badMimouna(), res_m);

        res = Solution.deleteMimouna(m);
        assertEquals(NOT_EXISTS, res);

        res_m = Solution.getMimouna(m.getId());
        assertEquals(Mimouna.badMimouna(), res_m);
    }

    @Test
    public void testAddMimounaList() {
        res = Solution.addMimounalist(MimounaList.badMimounalist());
        assertEquals(BAD_PARAMS, res);

        res = Solution.addMimounalist(MimounaList.badMimounalist());
        assertEquals(BAD_PARAMS, res);

        MimounaList ml = new MimounaList();
        ml.setId(236363);
        ml.setCity("Haifa");

        res = Solution.addMimounalist(ml);
        assertEquals(OK, res);

        res = Solution.addMimounalist(ml);
        assertEquals(ALREADY_EXISTS, res);

        ml.setCity("Tel Aviv");

        res = Solution.addMimounalist(ml);
        assertEquals(ALREADY_EXISTS, res);

        ml.setCity("Haifa");
        ml.setId(234123);

        res = Solution.addMimounalist(ml);
        assertEquals(OK, res);

        ml.setId(0);

        res = Solution.addMimounalist(ml);
        assertEquals(BAD_PARAMS, res);

        ml.setId(-4);

        res = Solution.addMimounalist(ml);
        assertEquals(BAD_PARAMS, res);

        ml.setId(236319);
        ml.setCity(null);

        res = Solution.addMimounalist(ml);
        assertEquals(BAD_PARAMS, res);

        Mimouna m = new Mimouna();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addMimouna(m);
        assertEquals(OK, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);
    }

    @Test
    public void testGetMimounaList() {
        MimounaList res_ml = Solution.getMimounalist(236363);
        assertEquals(MimounaList.badMimounalist(), res_ml);

        MimounaList ml = new MimounaList();
        ml.setId(236363);
        ml.setCity("Haifa");

        Mimouna m = new Mimouna();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addMimouna(m);
        assertEquals(OK, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.addMimounalist(ml);
        assertEquals(OK, res);

        res_ml = Solution.getMimounalist(234123);
        assertEquals(MimounaList.badMimounalist(), res_ml);

        res_ml = Solution.getMimounalist(236363);
        assertEquals(ml, res_ml);
    }

    @Test
    public void testDeleteMimounaList() {
        res = Solution.deleteMimounalist(MimounaList.badMimounalist());
        assertEquals(NOT_EXISTS, res);

        MimounaList ml = new MimounaList();
        ml.setId(236363);
        ml.setCity("Haifa");

        res = Solution.deleteMimounalist(ml);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addMimounalist(ml);
        assertEquals(OK, res);

        res = Solution.deleteMimounalist(ml);
        assertEquals(OK, res);

        res = Solution.deleteMimounalist(ml);
        assertEquals(NOT_EXISTS, res);

        Mimouna m = new Mimouna();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addMimouna(m);
        assertEquals(OK, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.deleteMimounalist(ml);
        assertEquals(NOT_EXISTS, res);

        res = Solution.deleteMimounalist(ml);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addMimounalist(ml);
        assertEquals(OK, res);

        res = Solution.deleteMimounalist(ml);
        assertEquals(OK, res);
    }
}
