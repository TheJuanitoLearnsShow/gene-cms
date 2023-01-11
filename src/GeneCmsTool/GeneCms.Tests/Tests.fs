module Tests

open System
open Xunit
open WordToHtml

[<Fact>]
let ``Convert Word Doc`` () =
   
    let filesToConvert =
            ["/Users/juanito/Library/CloudStorage/OneDrive-Personal/sources/PersonalBlogContents/Input/blog-entries/Add Sql Server Database Projects To CI.docx" ]
    WordConverterProvider.ConvertFilesToPages filesToConvert
        "/Users/juanito/Library/CloudStorage/OneDrive-Personal/sources/PersonalBlogContents/Output/blog-entries"
    Assert.True(true)


[<Fact>]
let ``Get Html Sections`` () =
    let baseFolder =
            "/Users/juanito/Library/CloudStorage/OneDrive-Personal/sources/PersonalBlogContents/Output/" 
    let sections = HtmlPartsToSite.DirectoryFileEntriesProvider.GetPageSections baseFolder
    printfn "%A" sections
    Assert.True(true)

