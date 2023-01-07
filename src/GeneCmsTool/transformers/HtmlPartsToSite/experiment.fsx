open System

let relativePath = "melo\\abc\\page test".Trim('\\')
let pagePrefix = "\\page "
let indexOfSlash = relativePath.IndexOf(pagePrefix)
let part1 = relativePath.Substring(0, indexOfSlash)
let part2 = relativePath.Substring(indexOfSlash + pagePrefix.Length)