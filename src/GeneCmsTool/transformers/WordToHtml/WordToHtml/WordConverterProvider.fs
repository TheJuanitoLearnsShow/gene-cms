namespace WordToHtml
open Mammoth
open System.IO
open System.Linq

module WordConverterProvider =
    let CreateEngine () =
        let converter =  DocumentConverter()
        converter
    let ConvertFile (converter:DocumentConverter) (inputFilePath:string) (outputFilePath:string) =
        let result = converter.ConvertToHtml(inputFilePath);
        let html = result.Value; // The generated HTML
        let outFolder = Path.GetDirectoryName outputFilePath
        if outFolder |> Directory.Exists |> not then
            Directory.CreateDirectory outFolder |> ignore
        File.WriteAllText(outputFilePath, html)

    let ConvertFiles (inputOutputPairs:(string*string) seq) =
        let converter = CreateEngine() |> ConvertFile 
        for (inputFile,outputFile) in (inputOutputPairs |> Seq.toList) do
            converter inputFile outputFile

    // files must be named "yyyy-MM-dd <title to use>.docx"
    let ConvertFilesToPages (wordInputFiles:(string) seq) (outputBaseFolder:string) =
        let inputOutputPairs =
            wordInputFiles
            |> Seq.map (fun f -> (f,Path.GetFileName))
            |> Seq.map (fun (input,filename: string) ->
                let orgFolder =
                    filename.Split(' ')[0]
                let finalFileName =
                    "Page " + filename.Split(' ').Last()
                Path.Combine(outputBaseFolder, orgFolder, finalFileName, "main.html")

            )
        ConvertFiles inputOutputPairs