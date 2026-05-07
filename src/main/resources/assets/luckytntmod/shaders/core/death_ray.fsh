#version 150

in vec2 vUV;

uniform float uTime;
uniform float uRadius;
uniform float uLength;
uniform vec3 uColor;
uniform float uNoiseStrength;
uniform float uAxialFalloffFactor;

out vec4 fragColor;

float hash(float n) {
    return fract(sin(n) * 43758.5453123);
}

float noise(vec2 x) {
    vec2 p = floor(x);
    vec2 f = fract(x);
    f = f * f * (3.0 - 2.0 * f);
    float n = p.x + p.y * 57.0;
    return uNoiseStrength * mix(
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
	float axialFalloff = smoothstep(0.0, 1.0 / uLength * uAxialFalloffFactor, vUV.x) * (1.0 - smoothstep(1.0 - 1.0 / uLength * uAxialFalloffFactor, 1.0, vUV.x));

    float n = noise(vec2(vUV.x * 10.0, uTime * 2.0));
    float pulse = sin(vUV.x * 20.0 - uTime * 5.0) * 0.5 + 0.75;
    float pulse2 = sin(vUV.x * 17.0 - uTime * 8.7) * 0.5 + 0.75;
	float lightning = radialFalloff * 0.5 * noise(vec2(vUV.x * uLength, vUV.y * 10.0) - uTime * 6.0) - dist * 0.25;
	
    float intensity = axialFalloff * (radialFalloff * (0.7 + 0.3 * n) * (0.75 + 0.5 * pulse * uNoiseStrength) * (0.75 + 0.5 * pulse2 * uNoiseStrength) + lightning * 2.0);

    vec3 color = uColor * intensity;
    fragColor = vec4(color, intensity);
}