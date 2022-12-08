namespace TableToRoutes

[<CLIMutable>]
type Route = {
    route:string
    allowedRoles: string array
}

[<CLIMutable>]
type PlatformErrorOverride = {
    errorType:string
    statusCode: string 
    serve: string
}


[<CLIMutable>]
type RoutesConfig = {
    routes: Route array
    platformErrorOverrides: PlatformErrorOverride array
}