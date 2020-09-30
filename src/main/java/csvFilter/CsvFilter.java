package csvFilter;

import java.util.ArrayList;
import java.util.List;




public class CsvFilter {

    private String decimalRegex = "\\d+(\\.\\d+)?";
    private String cifRegex = "^[a-zA-Z]{1}\\d{7}[a-zA-Z0-9]{1}$";
    private String nifRegex = "^\\d{8}[a-zA-Z]{1}$";

    public List<String> filter(List<String> lines) {
        List result = new ArrayList<String>();
        result.add(lines.get(0));
        List<String> repeatedInvoiceNumbers = getRepeatedInvoiceNumbers(lines);

        for (int i = 1; i < lines.size(); i++) {
            String invoice = lines.get(i);
            String[] fields = invoice.split(",");

            String invoiceNumberField = fields[0];
            String ivaField = fields[4];
            String igicField = fields[5];
            String cifField = fields[7];
            String nifField = fields[8];

            Boolean taxFieldsAreMutuallyExclusive = (ivaField.matches(decimalRegex) || igicField.matches(decimalRegex)) && (ivaField.isEmpty() || igicField.isEmpty());
            Boolean idFieldsAreMutuallyExclusive = (cifField.matches(cifRegex) || nifField.matches(nifRegex)) && ((cifField.isEmpty() || nifField.matches("\\s")));
            Boolean invoiceNumberExclusive = (!(repeatedInvoiceNumbers.contains(invoiceNumberField)));

            if (taxFieldsAreMutuallyExclusive && idFieldsAreMutuallyExclusive && invoiceNumberExclusive) {
                result.add(lines.get(i));
            }
        }


        return result;
    }

    private List<String> getRepeatedInvoiceNumbers(List<String> lines) {
        List<String> invoiceNumbers = new ArrayList<>();
        List<String> result = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String invoice = lines.get(i);
            String[] fields = invoice.split(",");
            invoiceNumbers.add(fields[0]);
        }

        for (int i = 0; i < invoiceNumbers.size(); i++) {
            for (int j = 0; j < invoiceNumbers.size(); j++) {
                if (i == j) {
                } else if (invoiceNumbers.get(j).equals(invoiceNumbers.get(i))){
                    result.add(invoiceNumbers.get(j));
                }
            }
        }
        return result;
    }
}