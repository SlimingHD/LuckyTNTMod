#version 150

in vec2 vUV;

uniform float uTime;
uniform float uRadius;
uniform float uLength;
uniform float uAxialFalloffFactor;
uniform vec3 uColor;
uniform vec3 uCenterColor;

out vec4 fragColor;

float hash(float n) {
    return fract(sin(n) * 43758.5453123);
}

float noise(vec2 x) {
    vec2 p = floor(x);
    vec2 f = fract(x);
    f = f * f * (3.0 - 2.0 * f);
    float n = p.x + p.y * 57.0;
    return mix(
        mix(hash(n), hash(n + 1.0), f.x),
        mix(hash(n + 57.0), hash(n + 58.0), f.x),
        f.y
    );
}

void main() {
	float k = 6.0 / uRadius;
	float norm = exp(-k);

    float dist = abs(vUV.y - 0.5) * 2.0;
    float falloff = exp(-dist * k);
    float radialFalloff = (falloff - norm) / (1.0 - norm);

    float pulse = sin(vUV.x * 21.0 - uTime * 7.0) * 0.5 + 0.75;
    float pulse2 = noise(vec2(vUV.y * 7.3 + uTime * 3.3, 0.0)) * sin(vUV.x * 77.0 - uTime * 23.0) * 0.5 + 0.75;
	
	float axialFalloff = smoothstep(0.0, 0.1, vUV.x) * (1.0 - smoothstep(0.9, 1.0, vUV.x));
	axialFalloff = mix(axialFalloff, 1.0, uAxialFalloffFactor);
    float intensity = (radialFalloff * (0.5 + 0.75 * pulse + 0.5 + 0.75 * pulse2)) * axialFalloff;

    vec3 color = mix(uColor, uCenterColor, pow(radialFalloff, 8.0)) * intensity;
    fragColor = vec4(color, intensity);
}