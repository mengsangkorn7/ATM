import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public byte[] hash(String pin) {
        byte[] generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            generatedPassword = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        return generatedPassword;

    }

    public boolean checkPassword(byte[] pin, String aPin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), pin);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

/*
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        MD5 h = new MD5();
        byte pw[] = h.hash("mengsang");
        System.out.println(pw);

        System.out.print("Enter pw: ");

        String aPin = sc.nextLine();
        System.out.println("aPin: " + aPin.getBytes());

        System.out.println(h.checkPassword(pw, aPin));

    }


 */

}




