import java.io.File;

public class Driver {
    public static void main(String [] args) {
        double[] coeffs1 = new double[]{1, 2, 5};
        int[] exps1 = new int[]{0, 1, 4};
        Polynomial p1 = new Polynomial(coeffs1, exps1);

        double[] coeff2 = new double[]{-1, 1,1,-1};
        int[] exps2 = new int[]{0,4,6, 7};
        Polynomial p2 = new Polynomial(coeff2,exps2);


        p2.printInfo();

        System.out.println(p2.hasRoot(1));
        System.out.println(p2.hasRoot(42));
        System.out.println("p2(1) = " + p2.evaluate(1));
        System.out.println("p1(1) = " + p1.evaluate(1));
        System.out.println("p1(2) = " + p1.evaluate(2));


        Polynomial p3 = p2.add(p1);

        p3.printInfo();

        p1.saveToFile("test.txt");

        File f = new File("test.txt");
        Polynomial p4 = new Polynomial(f);

        p4.printInfo();



    }
}
