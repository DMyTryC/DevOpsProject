package devopsproject;

import java.io.IOException;
import java.util.Optional;

public class DataFrameMain {

    public static void main(String[] args) throws IOException {
       DataFrame df = new DataFrame("tests/resources/format_error.csv",",");
               //df.groupby("Height",Optional.of("sum"));
    }

}
