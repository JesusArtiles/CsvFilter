package csvFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

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
        Boolean taxFieldsAreMutuallyExclusive = (ivaField.matches(decimalRegex) || igicField.matches(decimalRegex)) && (!(ivaField.matches(decimalRegex) && igicField.matches(decimalRegex)));
        if (taxFieldsAreMutuallyExclusive) {
            result.add(lines.get(1));
        }
        return result;
    }
}
