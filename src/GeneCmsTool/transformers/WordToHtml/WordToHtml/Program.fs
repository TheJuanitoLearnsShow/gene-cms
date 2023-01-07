// Learn more about F# at http://fsharp.org
namespace WordToHtml
open System
open System.IO;
open WordToHtml
open System.Text.Json

module Utils =
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
