// Learn more about F# at http://docs.microsoft.com/dotnet/fsharp

open System
open Utils
open MarkdownToHtml

// Define a function to construct a message to print
let from whom =
    sprintf "from %s" whom

[<EntryPoint>]
let main argv =
    let [| inputFolder; outputFolder |] = argv
    let filesToConvert = DirectoryFileEntriesProvider.GetInputAndOutputPairs inputFolder outputFolder
    MarkdownConverter.ConvertFiles filesToConvert
    0 // return an integer exit code