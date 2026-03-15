import { cva, type VariantProps } from "class-variance-authority"
import type { Icon } from "@phosphor-icons/react"

import { cn } from "@/lib/utils"

const toggleGroupVariants = cva(
  "inline-flex items-center gap-1 rounded-full border border-border bg-muted/50 p-1",
  {
    variants: {
      size: {
        default: "",
        sm: "gap-0.5 p-0.5",
      },
    },
    defaultVariants: {
      size: "default",
    },
  }
)

const toggleGroupItemVariants = cva(
  "cursor-pointer inline-flex items-center gap-1.5 rounded-full font-medium transition-all text-muted-foreground hover:text-foreground",
  {
    variants: {
      size: {
        default: "px-4 py-1.5 text-sm",
        sm: "px-3 py-1 text-xs",
      },
      active: {
        true: "bg-primary text-primary-foreground shadow-sm hover:text-primary-foreground",
        false: "",
      },
    },
    defaultVariants: {
      size: "default",
      active: false,
    },
  }
)

interface ToggleGroupOption<T extends string> {
  value: T
  label: string
  icon?: Icon
}

interface ToggleGroupProps<T extends string>
  extends VariantProps<typeof toggleGroupVariants> {
  value: T
  onValueChange: (value: T) => void
  options: ToggleGroupOption<T>[]
  className?: string
}

function ToggleGroup<T extends string>({
  value,
  onValueChange,
  options,
  size,
  className,
}: ToggleGroupProps<T>) {
  return (
    <div
      data-slot="toggle-group"
      className={cn(toggleGroupVariants({ size, className }))}
    >
      {options.map((option) => {
        const IconComp = option.icon
        const isActive = value === option.value
        return (
          <button
            key={option.value}
            type="button"
            data-state={isActive ? "on" : "off"}
            onClick={() => onValueChange(option.value)}
            className={cn(toggleGroupItemVariants({ size, active: isActive }))}
          >
            {IconComp && <IconComp className="size-4" />}
            {option.label}
          </button>
        )
      })}
    </div>
  )
}

export { ToggleGroup, toggleGroupVariants, toggleGroupItemVariants }
export type { ToggleGroupProps, ToggleGroupOption }
