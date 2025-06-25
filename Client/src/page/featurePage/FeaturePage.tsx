import { useState, useEffect } from "react";
import { features } from "../../data/featureData";
import { FeatureCard } from "../../component/feature/FeatureCard";

const FeaturePage = () => {
  const [activeFeature, setActiveFeature] = useState<number | null>(null);
  const [visibleElements, setVisibleElements] = useState<
    Record<string, boolean>
  >({});

  useEffect(() => {
    const timers = [
      setTimeout(
        () => setVisibleElements((prev) => ({ ...prev, header: true })),
        100
      ),
      ...Array(8)
        .fill(0)
        .map((_, i) =>
          setTimeout(
            () =>
              setVisibleElements((prev) => ({ ...prev, [`card${i}`]: true })),
            300 + i * 150
          )
        ),
    ];
    return () => timers.forEach(clearTimeout);
  }, []);

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-600 via-purple-600 to-purple-800 overflow-hidden">
      {/* Animated background elements */}
      <div className="absolute inset-0 overflow-hidden">
        <div className="absolute -top-40 -right-40 w-80 h-80 bg-white bg-opacity-10 rounded-full blur-3xl animate-pulse"></div>
        <div className="absolute -bottom-40 -left-40 w-80 h-80 bg-white bg-opacity-10 rounded-full blur-3xl animate-pulse delay-1000"></div>
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-96 h-96 bg-white bg-opacity-5 rounded-full blur-3xl animate-pulse delay-500"></div>
      </div>

      <div className="relative z-10 container mx-auto px-4 py-12">
        {/* Header */}
        <div
          className={`text-center mb-16 transition-all duration-1000 transform ${
            visibleElements.header
              ? "translate-y-0 opacity-100"
              : "translate-y-10 opacity-0"
          }`}
        >
          <h1 className="text-5xl md:text-6xl font-bold text-white mb-6">
            <span className="bg-gradient-to-r from-blue-300 to-purple-300 bg-clip-text text-transparent">
              Tính Năng
            </span>
          </h1>
          <p className="text-xl text-white max-w-2xl mx-auto leading-relaxed">
            Khám phá những tính năng đột phá giúp kết nối gia đình bạn gần gũi
            hơn bao giờ hết
          </p>
        </div>

        {/* Features Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
          {features.map((feature, index) => (
            <FeatureCard
              key={index}
              {...feature}
              index={index}
              isVisible={!!visibleElements[`card${index}`]}
              isActive={activeFeature === index}
              onHover={setActiveFeature}
            />
          ))}
        </div>

        {/* Call to Action */}
        <div
          className={`text-center mt-16 transition-all duration-1000 delay-1000 transform ${
            visibleElements.header
              ? "translate-y-0 opacity-100"
              : "translate-y-10 opacity-0"
          }`}
        >
          <div className="bg-blue-900/60 backdrop-blur-lg rounded-2xl p-8 border border-white border-opacity-10 max-w-2xl mx-auto">
            <h2 className="text-3xl font-bold text-white mb-4">
              Sẵn sàng kết nối gia đình?
            </h2>
            <p className="text-white mb-6">
              Tham gia FamilyMeet ngay hôm nay và trải nghiệm những tính năng
              tuyệt vời này
            </p>
            <button className="bg-gradient-to-r from-blue-500 to-purple-600 hover:from-blue-600 hover:to-purple-700 text-white font-bold py-3 px-8 rounded-xl transition-all duration-300 transform hover:scale-105 hover:shadow-lg">
              Bắt Đầu Ngay
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeaturePage;
