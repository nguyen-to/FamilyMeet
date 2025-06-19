import type { LucideProps } from "lucide-react";

interface InputProps {
  type?: "text" | "email" | "password" | "tel";
  value: string;
  onChange: (value: string) => void;
  placeholder?: string;
  label?: string;
  required?: boolean;
  icon?: React.ForwardRefExoticComponent<
    Omit<LucideProps, "ref"> & React.RefAttributes<SVGSVGElement>
  >;
  rightIcon?: React.ReactNode;
  error?: string;
  className?: string;
  id?: string; 
  name?: string; 
}

 const InputType = ({
  type = "text",
  value,
  onChange,
  placeholder,
  label,
  required = false,
  icon: Icon,
  rightIcon,
  error,
  className = "",
  id,
  name,
}: InputProps) => {
  const inputId = id || `input-${Math.random().toString(36).substr(2, 9)}`;

  return (
    <div className="space-y-2">
      {label && (
        <label htmlFor={inputId} className="text-white/90 text-sm font-medium">
          {label}
          {required && (
            <span className="text-red-400 ml-1" aria-hidden="true">
              *
            </span>
          )}
        </label>
      )}
      <div className="relative">
        {Icon && (
          <div
            className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
            aria-hidden="true"
          >
            <Icon className="h-5 w-5 text-white/50" />
          </div>
        )}
        <input
          id={inputId}
          name={name}
          type={type}
          value={value}
          onChange={(e) => onChange(e.target.value)}
          className={`w-full pl-10 pr-4 py-3 bg-white/10 border border-white/20 rounded-xl 
            text-white placeholder-white/50 focus:outline-none focus:ring-2 
            focus:ring-green-400 focus:border-transparent transition-all
            ${error ? "border-red-400 focus:ring-red-400" : ""}
            ${rightIcon ? "pr-12" : ""}
            ${className}`.trim()}
          placeholder={placeholder}
          required={required}
          aria-invalid={error ? "true" : "false"}
          aria-describedby={error ? `${inputId}-error` : undefined}
        />
        {rightIcon && (
          <div
            className="absolute inset-y-0 right-0 pr-3 flex items-center"
            aria-hidden="true"
          >
            {rightIcon}
          </div>
        )}
      </div>
      {error && (
        <p
          id={`${inputId}-error`}
          className="text-xs text-red-300"
          role="alert"
        >
          {error}
        </p>
      )}
    </div>
  );
};
export default InputType;