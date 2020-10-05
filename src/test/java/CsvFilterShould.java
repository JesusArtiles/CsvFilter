import csvFilter.CsvFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class CsvFilterShould {

    private String headerLine = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";
    private List<String> emptyDataFile = Arrays.asList(headerLine);
    private CsvFilter filter;

    @Before
    public void setup(){
        filter = new CsvFilter();
    }

    @Test
    public void allow_for_correct_lines_only(){
        List<String> lines = fileWithOneInvoiceLineHaving("19","","B76430134","");
        List<String> result = filter.apply(lines);
        assertThat(result).isEqualTo(lines);


    }

    @Test
    public void exclude_lines_with_both_tax_fields_populated_as_they_are_exclusive(){
        List<String> result = filter.apply(fileWithOneInvoiceLineHaving("19","8","B76430134",""));
        assertThat(result).isEqualTo(emptyDataFile);
    }

    @Test
    public void exclude_lines_with_both_tax_fields_empty_as_one_is_required(){
        List<String> result = filter.apply(fileWithOneInvoiceLineHaving("","","B76430134",""));
        assertThat(result).isEqualTo(emptyDataFile);
    }

    @Test
    public void exclude_lines_with_non_decimal_tax_fields() {
        List<String> result = filter.apply(fileWithOneInvoiceLineHaving("XYZ","","B76430134",""));
        assertThat(result).isEqualTo(emptyDataFile);

    }

    @Test
    public void exclude_lines_with_both_tax_fields_populated_even_if_non_decimal(){
        List<String> result = filter.apply(fileWithOneInvoiceLineHaving("XYZ","8","B76430134",""));
        assertThat(result).isEqualTo(emptyDataFile);

    }

    @Test
    public void exclude_lines_with_both_Id_fields_populated_as_they_are_exclusive(){
        List<String> result = filter.apply(fileWithOneInvoiceLineHaving("19","","B76430134","45777307Q"));
        assertThat(result).isEqualTo(emptyDataFile);

    }
    @Test
    public void allow_for_more_than_one_line(){
        String invoiceLine ="1,02/05/2019,1000,810,,12,ACER Laptop,B76430134, ";
        String invoiceLine2 ="2,02/05/2019,1000,810,,12,ACER Laptop,,45777307Q";
        String invoiceLine3 = "3,02/05/2019,1000,810,,12,ACER Laptop,B76430134,45777307Q";

        List<String> result = filter.apply(Arrays.asList(headerLine, invoiceLine, invoiceLine2, invoiceLine3));
        assertThat(result).isEqualTo(Arrays.asList(headerLine, invoiceLine, invoiceLine2));
    }

    @Test
    public void delete_all_lines_with_invoice_number_repetitions(){
        String invoiceLine ="1,02/05/2019,1000,810,,12,ACER Laptop,B76430134, ";
        String invoiceLine2 ="2,02/05/2019,1000,810,,12,ACER Laptop,,45777307Q";
        String invoiceLine3 ="1,02/05/2019,1000,810,,12,ACER Laptop,B76430134,45777307Q";

        List<String> result = filter.apply(Arrays.asList(headerLine, invoiceLine, invoiceLine2, invoiceLine3));
        assertThat(result).isEqualTo(Arrays.asList(headerLine, invoiceLine2));

    }

    private List<String> fileWithOneInvoiceLineHaving(String ivaTax, String igicTax,String cif, String nif){
        String invoiceId = "1";
        String invoiceDate = "02/05/2019";
        String grossAmount = "1000";
        String netAmount = "810";
        String concept = "irrelevant";
        List<String> formattedLine = Arrays.asList(
                invoiceId,
                invoiceDate,
                grossAmount,
                netAmount,
                ivaTax,
                igicTax,
                concept,
                cif,
                nif+" "
        );
        String newFormmatedLine = formattedLine.stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(","));

        return Arrays.asList(headerLine, newFormmatedLine);
    }

}
