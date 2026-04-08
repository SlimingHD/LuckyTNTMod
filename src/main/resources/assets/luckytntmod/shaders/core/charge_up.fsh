#version 150

in vec2 vUV;

uniform float uTime;
uniform float uRadius;
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
        mix(hash(n + 0.0), hash(n + 1.0), f.x),
        mix(hash(n + 57.0), hash(n + 58.0), f.x),
        f.y
    );
}

void main() {
	vec2 uv = vUV - 0.5;
	
	float dist = length(uv);
	float core = exp(-dist * 6.0);
	float pulse = sin(uv.x + uTime * 2.3) * 0.5 + sin(uv.y + uTime * 4.1) * 0.5;
	float n = noise(vec2(uv.x + uTime * 3.2, uv.y + uTime * 2.7));
	
	float intensity = core * 2.0 * (0.75 + pulse * 0.25) * (0.7 + n * 0.3);
	
	vec3 color = mix(uCenterColor, uColor, dist + 0.5) * intensity;
	fragColor = vec4(color, intensity);
}