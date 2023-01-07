// Learn more about F# at http://docs.microsoft.com/dotnet/fsharp

open System
open System.IO
open System.Text.Json
open TableToRoutes

// Define a function to construct a message to print
let from whom =
    sprintf "from %s" whom

[<EntryPoint>]
let main argv =
    let [| routesJsonFile; excelFilePath; outputFolder |] = argv
    let jsonStr = File.ReadAllText(routesJsonFile)
    let config = JsonSerializer.Deserialize<RoutesConfig>(jsonStr)
    
    let routesFromTable = ExcelTableProvider.GetRoutes excelFilePath
    let newRoutes =  config.routes |> Array.append routesFromTable
    let newConfig = { config with routes = newRoutes }

    let newJson = JsonSerializer.Serialize(newConfig)
    let newJsonPath = Path.Combine(outputFolder, "routes.json")
    File.WriteAllText(newJsonPath, newJson)
    0 // return an integer exit code