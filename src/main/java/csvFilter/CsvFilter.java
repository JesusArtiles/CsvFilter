package csvFilter;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;




public class CsvFilter {
    public List<String> filter(List<String> lines){
        List result = new ArrayList<String>();
        result.add(lines.get(0));
        String invoice = lines.get(1);
        String[] fields = invoice.split(",");

        String ivaField = fields[4];
        String igicField = fields[5];
        String cifField = fields[7];
        String nifField = fields[8];

        String decimalRegex = "\\d+(\\.\\d+)?";
        String cifRegex = "^[a-zA-Z]{1}\\d{7}[a-zA-Z0-9]{1}$";
        String nifRegex = "^\\d{8}[a-zA-Z]{1}$";

        Boolean taxFieldsAreMutuallyExclusive = (ivaField.matches(decimalRegex) || igicField.matches(decimalRegex)) && (ivaField.isEmpty() || igicField.isEmpty());
        Boolean idFieldsAreMutuallyExclusive = (cifField.matches(cifRegex) || nifField.matches(nifRegex)) && ((cifField.isEmpty() || nifField.matches("\\s")));

        if (taxFieldsAreMutuallyExclusive && idFieldsAreMutuallyExclusive) {
            result.add(lines.get(1));
        }
        return result;
    }
}


