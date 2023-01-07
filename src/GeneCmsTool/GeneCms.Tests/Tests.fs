module Tests

open System
open Xunit
open WordToHtml

[<Fact>]
let ``Convert Word Doc`` () =
    let filesToConvert =
            [("/Users/juanito/Library/CloudStorage/OneDrive-Personal/sources/PersonalBlogContents/Input/Add Sql Server Database Projects To CI.docx",
                "blog-entry.html") ]
    WordConverterProvider.ConvertFiles filesToConvert
    Assert.True(true)

