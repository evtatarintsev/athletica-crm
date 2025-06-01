import { ChevronRight } from "@mui/icons-material";

function Breadcrumbs({children}: { children: React.ReactNode }) {
    return (
        <nav className="hidden md:flex" aria-label="breadcrumb">
            <ol className="flex flex-wrap items-center gap-1.5 break-words text-sm text-muted-foreground sm:gap-2.5">
                {children}
            </ol>
        </nav>
    );
}

function BreadcrumbLink({title, href}: { title: string; href: string; }) {
    return (
        <li className="inline-flex items-center gap-1.5">
            <a href={href} className="transition-colors hover:text-foreground">
                {title}
            </a>
            <span
                role="presentation"
                aria-hidden="true"
                className="[&>svg]:size-3.5"
            >
                <ChevronRight/>
            </span>
        </li>
    );
}

function BreadcrumbPage({title}: { title: string }) {
    return (
        <li className="inline-flex items-center gap-1.5">
            <span
                role="link"
                aria-disabled="true"
                aria-current="page"
                className="font-normal text-foreground"
            >
                {title}
            </span>
        </li>
    );
}

export {Breadcrumbs, BreadcrumbLink, BreadcrumbPage}