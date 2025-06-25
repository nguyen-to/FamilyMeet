import type { LucideIcon } from "lucide-react";

interface FeatureCardProps {
  icon: LucideIcon;
  title: string;
  description: string;
  color: string;
  details: string[];
  isVisible: boolean;
  isActive: boolean;
  index: number;
  onHover: (index: number | null) => void;
}

export const FeatureCard = ({
  icon: Icon,
  title,
  description,
  color,
  details,
  isVisible,
  isActive,
  index,
  onHover,
}: FeatureCardProps) => (
  <div
    className={`group relative transition-all duration-700 transform ${
      isVisible
        ? "translate-y-0 opacity-100 scale-100"
        : "translate-y-10 opacity-0 scale-95"
    }`}
    onMouseEnter={() => onHover(index)}
    onMouseLeave={() => onHover(null)}
    style={{ transitionTimingFunction: "cubic-bezier(0.4, 0.2, 0.2, 1)" }} // mượt hơn
  >
    <div className="relative h-full bg-blue-900/60 backdrop-blur-lg rounded-2xl p-6 border border-white border-opacity-10 transition-all duration-500 ease-[cubic-bezier(0.4,0.2,0.2,1)] cursor-pointer group-hover:scale-[1.045] group-hover:shadow-2xl group-hover:shadow-purple-500/20 group-hover:-translate-y-1 group-hover:border-opacity-30 group-hover:bg-blue-900/80">
      <div
        className={`w-16 h-16 rounded-xl bg-gradient-to-r ${color} p-3 mb-4 transition-transform duration-500 ease-[cubic-bezier(0.4,0.2,0.2,1)] group-hover:scale-110`}
      >
        <div className="text-white w-full h-full flex items-center justify-center">
          <Icon className="w-8 h-8" />
        </div>
      </div>
      <h3 className="text-xl font-bold text-white mb-3 group-hover:text-blue-100 transition-colors duration-300">
        {title}
      </h3>
      <p className="text-white text-sm leading-relaxed mb-4 opacity-90">
        {description}
      </p>
      <div
        className={`transition-all duration-300 overflow-hidden ${
          isActive ? "max-h-40 opacity-100" : "max-h-0 opacity-0"
        }`}
      >
        <div className="border-t border-white border-opacity-20 pt-4 mt-4">
          <ul className="space-y-2">
            {details.map((detail, i) => (
              <li key={i} className="text-blue-100 text-xs flex items-center">
                <div className="w-1.5 h-1.5 rounded-full bg-blue-300 mr-2"></div>
                {detail}
              </li>
            ))}
          </ul>
        </div>
      </div>
      <div
        className={`absolute inset-0 rounded-2xl bg-gradient-to-r ${color} opacity-0 group-hover:opacity-20 transition-opacity duration-300 -z-10`}
      ></div>
    </div>
  </div>
);
