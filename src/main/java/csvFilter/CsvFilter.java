package csvFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

public class CsvFilter {
    public List<String> filter(List<String> lines){
        List result = new ArrayList<String>();
        result.add(lines.get(0));
        String invoice = lines.get(1);
        String[] fields = invoice.split(",");
        int ivaField = 4;
        int igicField = 5;
        if ((fields[ivaField].isEmpty() || fields[igicField].isEmpty()) && (!(fields[ivaField].isEmpty() && fields[igicField].isEmpty()))){
            result.add(lines.get(1));
        }
        return result;
    }
}
