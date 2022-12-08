namespace MarkdownToHtml
open Markdig
open System.IO

module MarkdownConverter =
    
    let CreateEngine () =
        let pipeline = MarkdownPipelineBuilder().UseAdvancedExtensions().Build();
        (fun contents -> Markdown.ToHtml(contents, pipeline))
        
    let ConvertFile (converter:string->string) (inputFilePath:string, outputFilePath:string) =
        async {
            let! contents = File.ReadAllTextAsync inputFilePath |> Async.AwaitTask
            let html = converter(contents);
            let outFolder = Path.GetDirectoryName outputFilePath
            if outFolder |> Directory.Exists |> not then
                Directory.CreateDirectory outFolder |> ignore
            File.WriteAllText(outputFilePath, html)
        }

    let ConvertFiles (inputOutputPairs:(string*string) seq) =
        let converter = CreateEngine() |> ConvertFile 
        inputOutputPairs 
            |> Seq.toList
            |> List.map converter
            |> Async.Parallel
            |> Async.RunSynchronously