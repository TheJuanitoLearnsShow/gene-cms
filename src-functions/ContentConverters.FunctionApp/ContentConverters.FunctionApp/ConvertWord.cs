using System;
using System.IO;
using System.Threading.Tasks;
using Mammoth;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Azure.WebJobs;
using Microsoft.Azure.WebJobs.Extensions.Http;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.JsonPatch.Internal;
using Microsoft.Azure.WebJobs.Host;
using Microsoft.Extensions.Logging;

namespace ContentConverters.FunctionApp;

public static class ConvertWord
{
    [FunctionName("ConvertWord")]
    public static async Task<IActionResult> RunAsync(
        [HttpTrigger(AuthorizationLevel.Function,  "post", 
            Route = "files/{fileName}")] HttpRequest req, ILogger log, string fileName)
    {
        var converter = new DocumentConverter()
            .AddStyleMap("p[style-name='Code'] => pre:separator('\\n')");
        var result = converter.ConvertToHtml(req.Body);
        var html = result.Value; // The generated HTML
        var warnings = result.Warnings; // Any warnings during conversion


        return new OkObjectResult(
            new EntryConversionResult() { Title = Path.GetFileNameWithoutExtension(fileName), 
                Contents = html }
            );
    }
}

public class EntryConversionResult
{
    public string Title { get; set; }
    public string Contents { get; set; }
}