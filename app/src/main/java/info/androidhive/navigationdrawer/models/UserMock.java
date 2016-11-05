package info.androidhive.navigationdrawer.models;

import com.orm.SugarRecord;

/**
 * Created by raul on 05/11/2016.
 */

public class UserMock extends SugarRecord {
    private String email;
    private String password;

     /**
     *
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
