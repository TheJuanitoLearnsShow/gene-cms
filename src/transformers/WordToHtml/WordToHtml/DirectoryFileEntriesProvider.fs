namespace WordToHtml

open System.IO

module DirectoryFileEntriesProvider =
    let GetInputAndOutputPairs (baseInputFolder:string) (baseOutputFolder:string) =
        let files = Directory.EnumerateFiles(baseInputFolder, "*.docx", SearchOption.AllDirectories )
        files
        |> Seq.map (fun f -> 
            let relativePath  = 
                Path.GetRelativePath( baseInputFolder, f)
                |> Path.GetDirectoryName
            let newFilePath = 
                Path.Combine(baseOutputFolder,  relativePath
                , Path.GetFileNameWithoutExtension(f).ToLower()
                + ".html"
                ).ToLower()
            (f,newFilePath)
        )