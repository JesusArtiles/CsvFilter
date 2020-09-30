package csvFilter;

import java.util.ArrayList;
import java.util.List;




public class CsvFilter {

    private String decimalRegex = "\\d+(\\.\\d+)?";
    private String cifRegex = "^[a-zA-Z]{1}\\d{7}[a-zA-Z0-9]{1}$";
    private String nifRegex = "^\\d{8}[a-zA-Z]{1}$";

    public List<String> filter(List<String> lines){
        List result = new ArrayList<String>();
        result.add(lines.get(0));

        for(int i = 1; i < lines.size();i++){
            String invoice = lines.get(i);
            String[] fields = invoice.split(",");

            String ivaField = fields[4];
            String igicField = fields[5];
            String cifField = fields[7];
            String nifField = fields[8];

            Boolean taxFieldsAreMutuallyExclusive = (ivaField.matches(decimalRegex) || igicField.matches(decimalRegex)) && (ivaField.isEmpty() || igicField.isEmpty());
            Boolean idFieldsAreMutuallyExclusive = (cifField.matches(cifRegex) || nifField.matches(nifRegex)) && ((cifField.isEmpty() || nifField.matches("\\s")));

            if (taxFieldsAreMutuallyExclusive && idFieldsAreMutuallyExclusive) {
                result.add(lines.get(i));
            }
        }

        return result;
    }
}


