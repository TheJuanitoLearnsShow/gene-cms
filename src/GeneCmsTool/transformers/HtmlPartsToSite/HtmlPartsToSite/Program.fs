// Learn more about F# at http://docs.microsoft.com/dotnet/fsharp

open System
open HtmlPartsToSite

// Define a function to construct a message to print
let from whom =
    sprintf "from %s" whom

[<EntryPoint>]
let main argv =
    let [| baseInputFolder; baseOutputFolder; templateFilePath |]= argv
    let sections = DirectoryFileEntriesProvider.GetPageSections baseInputFolder
    HtmlFileGenerator.WriteHtmlFiles baseOutputFolder templateFilePath sections
    |> ignore
    0 // return an integer exit code