namespace Utils

open System.IO

module DirectoryFileEntriesProvider =
    let mask = "*.md"
    let outputExtension = ".html"
    let GetInputAndOutputPairs (baseInputFolder:string) (baseOutputFolder:string) =
        let files = Directory.EnumerateFiles(baseInputFolder, mask, SearchOption.AllDirectories )
        files
        |> Seq.map (fun f -> 
            let relativePath  = 
                Path.GetRelativePath( baseInputFolder, f)
                |> Path.GetDirectoryName
            let newFilePath = 
                Path.Combine(baseOutputFolder,  relativePath
                , Path.GetFileNameWithoutExtension(f).ToLower()
                + outputExtension
                ).ToLower()
            (f,newFilePath)
        )