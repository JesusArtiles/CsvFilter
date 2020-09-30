import csvFilter.CsvFilter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;



public class CsvFilterShould {

    String headerLine = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";

    @Test
    public void allow_for_correct_lines_only(){
        String invoiceLine = "1,02/05/2019,1000,810,19,,ACER Laptop,B76430134, ";

        List<String> result = new CsvFilter().filter(Arrays.asList(headerLine, invoiceLine));
        assertEquals(Arrays.asList(headerLine, invoiceLine),result);


    }

    @Test
    public void exclude_lines_with_both_tax_fields_populated_as_they_are_exclusive(){
        String invoiceLine = "1,02/05/2019,1000,810,19,8,ACER Laptop,B76430134, ";

        List<String> result = new CsvFilter().filter(Arrays.asList(headerLine, invoiceLine));
        assertEquals(Arrays.asList(headerLine),result);
    }

    @Test
    public void exclude_lines_with_both_tax_fields_empty_as_one_is_required(){
        String invoiceLine = "1,02/05/2019,1000,810,,,ACER Laptop,B76430134, ";

        List<String> result = new CsvFilter().filter(Arrays.asList(headerLine, invoiceLine));
        assertEquals(Arrays.asList(headerLine),result);

    }

    @Test
    public void exclude_lines_with_non_decimal_tax_fields() {
        String invoiceLine = "1,02/05/2019,1000,810,XYZ,,ACER Laptop,B76430134, ";

        List<String> result = new CsvFilter().filter(Arrays.asList(headerLine, invoiceLine));
        assertEquals(Arrays.asList(headerLine),result);
    }

    @Test
    public void exclude_lines_with_both_tax_fields_populated_even_if_non_decimal(){
        String invoiceLine ="1,02/05/2019,1000,810,XYZ,12,ACER Laptop,B76430134, ";

        List<String> result = new CsvFilter().filter(Arrays.asList(headerLine, invoiceLine));
        assertEquals(Arrays.asList(headerLine),result);
    }

    @Test
    public void exclude_lines_with_both_Id_fields_populated_as_they_are_exclusive(){
        String invoiceLine ="1,02/05/2019,1000,810,,12,ACER Laptop,B76430134,45777307Q";

        List<String> result = new CsvFilter().filter(Arrays.asList(headerLine, invoiceLine));
        assertEquals(Arrays.asList(headerLine),result);
    }
    @Test
    public void allow_for_more_than_one_line(){
        String invoiceLine ="1,02/05/2019,1000,810,,12,ACER Laptop,B76430134, ";
        String invoiceLine2 ="2,02/05/2019,1000,810,,12,ACER Laptop,,45777307Q";
        String invoiceLine3 = "3,02/05/2019,1000,810,,12,ACER Laptop,B76430134,45777307Q";

        List<String> result = new CsvFilter().filter(Arrays.asList(headerLine, invoiceLine, invoiceLine2, invoiceLine3));
        assertEquals(Arrays.asList(headerLine, invoiceLine, invoiceLine2),result);

    }

    @Test
    public void delete_all_lines_with_invoice_number_repetitions(){
        String invoiceLine ="1,02/05/2019,1000,810,,12,ACER Laptop,B76430134, ";
        String invoiceLine2 ="2,02/05/2019,1000,810,,12,ACER Laptop,,45777307Q";
        String invoiceLine3 ="1,02/05/2019,1000,810,,12,ACER Laptop,B76430134,45777307Q";

        List<String> result = new CsvFilter().filter(Arrays.asList(headerLine, invoiceLine, invoiceLine2, invoiceLine3));
        assertEquals(Arrays.asList(headerLine, invoiceLine2),result);

    }


}
