import * as React from "react"
import {twMerge} from "tailwind-merge"

interface CardProps extends React.HTMLAttributes<HTMLDivElement> {
}

interface CardHeaderProps extends React.HTMLAttributes<HTMLDivElement> {
}

interface CardTitleProps extends React.HTMLAttributes<HTMLHeadingElement> {
}

interface CardDescriptionProps extends React.HTMLAttributes<HTMLParagraphElement> {
}

interface CardContentProps extends React.HTMLAttributes<HTMLDivElement> {
}

interface CardFooterProps extends React.HTMLAttributes<HTMLDivElement> {
}

function Card({className, ...props}: CardProps) {

    return (
        <div
            className={twMerge("rounded-lg border bg-card text-card-foreground shadow-sm", className)}
            {...props}
        />
    )
}

function CardHeader({className, ...props}: CardHeaderProps) {
    return (
        <div
            className={twMerge("flex flex-col space-y-1.5 p-6", className)}
            {...props}
        />
    )
}

function CardTitle({className, ...props}: CardTitleProps) {
    return (
        <h3
            className={twMerge(
                "text-2xl font-semibold leading-none tracking-tight",
                className
            )}
            {...props}
        />
    )
}

function CardDescription({className, ...props}: CardDescriptionProps) {
    return (
        <p
            className={twMerge("text-sm text-muted-foreground", className)}
            {...props}
        />
    )
}

function CardContent({className, ...props}: CardContentProps) {
    return (
        <div className={twMerge("p-6 pt-0", className)} {...props} />
    )
}

function CardFooter({className, ...props}: CardFooterProps) {
    return (
        <div
            className={twMerge("flex items-center p-6 pt-0", className)}
            {...props}
        />
    )
}

export {Card, CardHeader, CardFooter, CardTitle, CardDescription, CardContent}