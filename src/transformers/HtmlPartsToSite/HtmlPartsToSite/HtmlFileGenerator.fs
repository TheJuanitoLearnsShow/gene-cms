namespace HtmlPartsToSite

open System.IO
open HtmlAgilityPack

module HtmlFileGenerator =
    let WriteHtmlFileFromSections  (baseOutputFolder:string) (templateFilePath:string) (pageRelativePath: string) 
        (sections:PageSection seq) =
        async {
            let doc = HtmlDocument();
            doc.Load(templateFilePath)
            for (_, sectionId, contents) in sections do
                let elem = doc.GetElementbyId(sectionId)
                elem.InnerHtml <- contents
            let outFolder = Path.Combine(baseOutputFolder, pageRelativePath)
            let htmlFilePath = Path.Combine(outFolder, "index.html")
            if outFolder |> Directory.Exists |> not then
                Directory.CreateDirectory outFolder |> ignore
            doc.Save(htmlFilePath)
        }
    let WriteHtmlFiles (baseOutputFolder:string) (templateFilePath:string) 
        (sectionGrps:(string*(PageSection array)) array) =
        let writeFile = WriteHtmlFileFromSections baseOutputFolder templateFilePath
        let comps =
            sectionGrps
            |> Array.map (fun (pageRelativePath,sections) ->
                writeFile pageRelativePath sections
            )
        comps 
        |> Async.Parallel
        |> Async.RunSynchronously
        