import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;

public class Polynomial{

    private double[] coefficients;
    private int[] exponents;

    public Polynomial(){
        this.coefficients = new double[0];
        this.exponents = new int[0];
    }

    public Polynomial(File file){
        try{
            BufferedReader input = new BufferedReader(new FileReader(file));
            String line = input.readLine();
            input.close();
            
            String[] parts = line.replace("-", "+-").split("\\+");
            double[] tempCo = new double[parts.length];
            int[] tempExp = new int[parts.length];

            int curr = 0;
            for (int i = 0; i < parts.length; i++){

                double num;
                int exp;

                if (parts[i].isEmpty()) continue;

                int xPos = parts[i].indexOf('x');
            
                if (xPos == -1){
                    num = Double.parseDouble(parts[i]);
                    exp = 0;
                }
                else{

                    String before = parts[i].substring(0, xPos);
                    String after = parts[i].substring(xPos+1);

                    if (before.equals("")){
                        num = 1;
                    }
                    else if (before.equals("-")){
                        num = -1;
                    }
                    else{
                        num = Double.parseDouble(before);
                    }

                    if (after.equals("")){
                        exp = 1;
                    }else{
                        exp = Integer.parseInt(after);
                    }
                }

                tempCo[curr] = num;
                tempExp[curr] = exp;
                curr++;
            }

            this.coefficients = Arrays.copyOf(tempCo, curr);
            this.exponents = Arrays.copyOf(tempExp, curr);
        }
        catch (IOException e){
            System.out.println("Error Cannot find File");
        }
    }

    public Polynomial(double[] arr, int[] exps){

        if (arr.length != exps.length){
            System.out.println("Error: Length of exponents and coefficients must be the same");
            this.coefficients = new double[0];
            this.exponents = new int[0];
            return;
        }

        this.coefficients = new double[arr.length];
        this.exponents = new int[exps.length];

        for (int i = 0; i < arr.length; i++){
            this.coefficients[i] = arr[i];
            this.exponents[i] = exps[i];
        }

    }

    public Polynomial add(Polynomial p2){   

        int len1 = this.coefficients.length;
        int len2 = p2.coefficients.length;
        
        double[] newCoArr = new double[len1 + len2];
        int[] newExpArr = new int[len1 + len2];
        int a = len1;

        for (int i = 0; i < len1; i++ ){
            newCoArr[i] = this.coefficients[i];
            newExpArr[i] = this.exponents[i];
        }

        boolean found = false;

        for (int j = 0; j < len2; j++){
            found = false;
            for (int k = 0; k < a; k++){
                if (p2.exponents[j] == newExpArr[k]){
                    newCoArr[k] += p2.coefficients[j];
                    found = true;
                    break;
                }
            }

            if (!found){
                newCoArr[a] = p2.coefficients[j];
                newExpArr[a] = p2.exponents[j];
                a++;
            }
        }

        double[] cleanCoArr = new double[a];
        int[] cleanExpArr = new int[a];
        int curr = 0;
        for (int n = 0; n<a; n++){
            if (Math.abs(newCoArr[n]) < 1e-10){
                continue;
            }

            cleanCoArr[curr] = newCoArr[n];
            cleanExpArr[curr] = newExpArr[n];
            curr++;
        }

        return new Polynomial(Arrays.copyOf(cleanCoArr, curr), Arrays.copyOf(cleanExpArr, curr));
    }

    public double evaluate(double num){
        double result = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(num, this.exponents[i]);
        }

        return result;
    }

    public boolean hasRoot(double num){
        return Math.abs(evaluate(num)) < 1e-10;
    }

    public Polynomial multiply(Polynomial p2){
        int len1 = this.coefficients.length;
        int len2 = p2.coefficients.length;
        int a = len1*len2;

        double[] tempCoArr = new double[a];
        int[] tempExpArr = new int[a];
        int curr = 0;

        for (int i = 0; i < len1; i++){
            double num1 = this.coefficients[i];
            int exp1 = this.exponents[i];

            for(int j = 0; j < len2; j++){
                double num2 = p2.coefficients[j];
                int exp2 = p2.exponents[j];

                tempCoArr[curr] = num1 * num2;
                tempExpArr[curr] = exp1 + exp2;
                curr++;
            }
        }

        for (int i = 0; i < curr; i++){
            if (Math.abs(tempCoArr[i]) <= 1e-10) continue;
            for (int j = i+1; j < curr; j++){
                if (tempExpArr[i] == tempExpArr[j]){
                    tempCoArr[i] += tempCoArr[j];
                    tempCoArr[j] = 0;
                }
            }
        }

        double[] cleanCoArr = new double[curr];
        int[] cleanExpArr = new int[curr];
        int c = 0;
        for (int i = 0; i < curr; i++){
            if (Math.abs(tempCoArr[i]) < 1e-10){
                continue;
            }
            cleanCoArr[c] = tempCoArr[i];
            cleanExpArr[c] = tempExpArr[i];
            c++;
        }

        return new Polynomial(Arrays.copyOf(cleanCoArr,c), Arrays.copyOf(cleanExpArr,c));

    }

    public void saveToFile(String fileName){
        try{
            FileWriter writer = new FileWriter(fileName);
            
            for (int i = 0; i < this.coefficients.length; i++){
                double c = this.coefficients[i];
                int exp = this.exponents[i];

                boolean neg = c < 0;
                double value = Math.abs(c);

                if (i != 0){
                    if (neg){
                        writer.write("-");
                    }
                    else{
                        writer.write("+");
                    }
                }
                else{
                    if (neg){
                        writer.write("-");
                    }
                }

                if (exp == 0){
                    writer.write(Double.toString(value));
                }
                else {

                    if (value != 1){
                        writer.write(Double.toString(value));
                    }

                    writer.write("x");

                    if (exp!=1){
                        writer.write(Integer.toString(exp));
                    }
                }

            }
            writer.close();
        }
        catch(IOException e){
            System.out.println("Error");
        }
    }

    public void printInfo(){
        System.out.println(Arrays.toString(this.coefficients));
        System.out.println(Arrays.toString(this.exponents));
    }
}