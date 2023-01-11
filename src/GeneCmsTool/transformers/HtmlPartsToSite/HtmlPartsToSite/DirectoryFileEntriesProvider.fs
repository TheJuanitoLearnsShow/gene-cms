namespace HtmlPartsToSite

open System.IO

module DirectoryFileEntriesProvider =
    let pagePrefix = "\\page "
    let GetHtmlSections (baseInputFolder:string) (pageFolder:string) =
        let files = Directory.EnumerateFiles(pageFolder, "*.html", SearchOption.TopDirectoryOnly )
        files
        |> Seq.toList
        |> List.map (fun f -> 
            async {
                let folderRelativePath = Path.GetRelativePath(baseInputFolder, pageFolder).TrimEnd('\\')
                
                let indexOfSlash = folderRelativePath.IndexOf(pagePrefix)
                let pageRelativePath =
                    if indexOfSlash >= 0 then
                        let part1 = folderRelativePath.Substring(0, indexOfSlash)
                        let part2 = folderRelativePath.Substring(indexOfSlash + pagePrefix.Length)
                        part1 + "\\" + part2
                    else
                        folderRelativePath.Substring( pagePrefix.Length - 1)
                let! contents = File.ReadAllTextAsync f |> Async.AwaitTask
                let sectionName = Path.GetFileNameWithoutExtension f
                return (pageRelativePath, sectionName, contents)
            }
        )
    // Expects a page to be a folder (named "Page xxxxxx") that has multiple html or misc files
    //  and each of those files to be named as the html id of the html element
    //  to replace in the output page
    let GetPageSections (baseInputFolder:string)  =
        let pageFolders = Directory.EnumerateDirectories(baseInputFolder, "Page *",SearchOption.AllDirectories)
        pageFolders
        |> Seq.toList
        |> List.collect (GetHtmlSections baseInputFolder)
        |> Async.Parallel
        |> Async.RunSynchronously
        |> Array.groupBy (fun (pageRelativePath,sectionName, contents) -> pageRelativePath )