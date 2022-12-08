// Learn more about F# at http://fsharp.org

open System
open Mammoth;
open System.IO;
open WordToHtml
open System.Text.Json
open OneDriveProvider

[<EntryPoint>]
let main argv =

    
    // let jsonStr = File.ReadAllText("appsettings-secrets.json")
    // let config = JsonSerializer.Deserialize<LoaderConfig>(jsonStr)

    // let files = 
    //     DocumentProvider.CreateAuthClient(config)
    //     |> DocumentProvider.CreateGraphClient
    //     |> DocumentProvider.GetFilesFromFolder "root:/PersonalSite/WordArticles:"
    //     |> Async.AwaitTask
    //     |> Async.RunSynchronously
    
    // for f in files do
    //     printfn "%s" f.Name 
    let [| inputFolder; outputFolder |] = argv
    let filesToConvert = DirectoryFileEntriesProvider.GetInputAndOutputPairs inputFolder outputFolder
    WordConverterProvider.ConvertFiles filesToConvert
    0 // return an integer exit code
