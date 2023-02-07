package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;

public class Converter {
    
    /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String result = "{}"; // default return value; replace later!
        
        try {
        
            // INSERT YOUR CODE HERE
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            JSONObject jsonObject = new JSONObject();
            JSONArray colHeaders = new JSONArray(); 
            JSONArray rowHeaders = new JSONArray(); 
            JSONArray data = new JSONArray();            
            
            JSONArray current;
            String[] line = iterator.next();
            
            for (int i = 0; i < line.length; ++i) {     // Column headings
                colHeaders.add(line[i]);
            }
            
            while (iterator.hasNext()) {        // Iterate through all records
                line = iterator.next();         // Get next row
                current = new JSONArray();
                rowHeaders.add(line[0]);        // Row headings
                
                for (int j = 1; j < line.length; ++j) { 
                    int data_convert = Integer.parseInt(line[j]);   //Convert Data from String to Integer
                    current.add(data_convert);
                }
                data.add(current);                   
            }
            jsonObject.put("colHeaders", colHeaders);
            jsonObject.put("rowHeaders", rowHeaders);
            jsonObject.put("data", data);
            
            results = JSONValue.toJSONString(jsonObject);
                    
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        
        String result = ""; // default return value; replace later!
        
        try {
            
            // INSERT YOUR CODE HERE
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
            
            JSONParser parser = new JSONParser();
           JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
           
           JSONArray colHeadersJA = (JSONArray)jsonObject.get("colHeaders");
           JSONArray rowHeadersJA = (JSONArray)jsonObject.get("rowHeaders");
           JSONArray dataJA = (JSONArray)jsonObject.get("data");
           
           int colsize = colHeadersJA.size();
           int rowsize = rowHeadersJA.size();

           String[] colHeaders = new String[colsize];
           
           for (int i = 0; i < colsize; ++i) {
               colHeaders[i] = (String)colHeadersJA.get(i);     //Convert Object to String
           }
           csvWriter.writeNext(colHeaders);
           
           for (int i = 0; i < rowsize; ++i) {
               JSONArray data_line = (JSONArray)dataJA.get(i);
               String[] current = new String[colsize];
               current[0] = (String)rowHeadersJA.get(i);
               
               for (int j = 0; j < data_line.size(); ++j) {
                   current[j+1] = Long.toString((long) data_line.get(j));
               }
               
               csvWriter.writeNext(current);
           }
           
           results = writer.toString();
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
} 
