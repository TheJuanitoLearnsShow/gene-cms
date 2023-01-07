namespace WordToHtml
open Mammoth
open System.IO

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