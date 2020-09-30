package csvFilter;

import java.util.ArrayList;
import java.util.List;


public class CsvFilter {
    public List<String> filter(List<String> lines){
        List result = new ArrayList<String>();
        result.add(lines.get(0));
        String invoice = lines.get(1);
        String[] fields = invoice.split(",");
        int ivaFieldIndex = 4;
        int igicFieldIndex = 5;
        String ivaField = fields[ivaFieldIndex];
        String igicField = fields[igicFieldIndex];
        String decimalRegex = "\\d+(\\.\\d+)?";
        Boolean taxFieldsAreMutuallyExclusive = (ivaField.matches(decimalRegex) || igicField.matches(decimalRegex)) && (ivaField.isEmpty() || igicField.isEmpty());
        if (taxFieldsAreMutuallyExclusive) {
            result.add(lines.get(1));
        }
        return result;
    }
}
