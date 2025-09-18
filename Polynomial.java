
public class Polynomial{

    private double[] coefficients;

    public Polynomial(){
        this.coefficients = new double[1];
        this.coefficients[0] = 0;
    }

    public Polynomial(double[] arr){
        this.coefficients = new double[arr.length];
        for (int i = 0; i < arr.length; i++){
            this.coefficients[i] = arr[i];
        }
    }

    public Polynomial add(Polynomial p2){
        int len1 = this.coefficients.length;
        int len2 = p2.coefficients.length;
        
        int length = Math.max(len1,len2);
        double[] resultArr = new double[length];

        if (len1 == len2){
            for (int i = 0; i < len1;i++){
                resultArr[i] = this.coefficients[i] + p2.coefficients[i];
            }
        }
        else if (len1 > len2){
            for (int i = 0; i < len2; i++){
                resultArr[i] = this.coefficients[i] + p2.coefficients[i];
            }

            for (int j = len2; j < len1;j++){
                resultArr[j] = this.coefficients[j];
            }
        }
        else{
            for (int i = 0; i < len1; i++){
                resultArr[i] = this.coefficients[i] + p2.coefficients[i];
            }

            for (int j = len1; j < len2;j++){
                resultArr[j] = p2.coefficients[j];
            }
        }

        return new Polynomial(resultArr);
    }

    public double evaluate(double num){
        double result = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(num, i);
        }

        return result;
    }

    public boolean hasRoot(double num){
        return evaluate(num) == 0;
    }
}