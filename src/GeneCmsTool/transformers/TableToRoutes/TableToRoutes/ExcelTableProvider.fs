namespace TableToRoutes
open ExcelDataReader
open System.IO

module ExcelTableProvider =
    let GetRoutes (excelFilePath:string) = 
        System.Text.Encoding.RegisterProvider(System.Text.CodePagesEncodingProvider.Instance);
    
        use stream = File.Open(excelFilePath, FileMode.Open, FileAccess.Read)

        // Auto-detect format, supports:
        //  - Binary Excel files (2.0-2003 format; *.xls)
        //  - OpenXml Excel files (2007 format; *.xlsx, *.xlsb)
        use reader = ExcelReaderFactory.CreateReader(stream)
        //header
        reader.Read() |> ignore
        let rows = 
            seq { 
                while reader.Read() do 
                    {
                        route = reader.GetString(0)
                        allowedRoles = reader.GetString(1).Split(',') |> Array.map (fun s -> s.Trim())
                    }
            }
            |> Seq.toArray
        reader.Dispose()
        rows

            