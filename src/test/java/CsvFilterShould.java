import csvFilter.CsvFilter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;



public class CsvFilterShould {

    @Test
    public void allow_for_correct_lines_only(){
        String headerLine = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";
        String invoiceLine = "1,02/05/2019,1000,810,19,,ACER Laptop,B76430134,";

        List<String> result = new CsvFilter().filter(Arrays.asList(headerLine, invoiceLine));
        assertEquals(Arrays.asList(headerLine, invoiceLine),result);


    }
    


}
