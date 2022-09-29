import { LitElement, html } from "lit-element";
//import "https://unpkg.com/maplibre-gl@2.4.0/dist/maplibre-gl.js";
import { Map as MapLibreComponent, GeolocateControl } from "maplibre-gl";

class MapLibre extends LitElement {
  map!: MapLibreComponent;
  private $server?: MapLibreServerInterface;
  static get properties() {
    return {
      name: { type: String },
    };
  }

  addGeoJSON(id: string, source: any) {
    this.map.addSource(id, { type: "geojson", data: source });
  }

  addExtrudedLayer(id: string, source: any) {
    this.map.addLayer({
      id: id,
      type: "fill-extrusion",
      source: source,
      layout: {},
      paint: {
        "fill-extrusion-color": "#aaa",
        "fill-extrusion-height": [
          "interpolate",
          ["linear"],
          ["zoom"],
          14,
          0,
          20.05,
          ["get", "height"],
        ],
        "fill-extrusion-base": [
          "interpolate",
          ["linear"],
          ["zoom"],
          14,
          0,
          20.05,
          ["get", "min_height"],
        ],
        "fill-extrusion-opacity": 0.95,
      },
    });
  }

  render() {
    return html`
    <link rel="stylesheet" href="https://unpkg.com/maplibre-gl@2.4.0/dist/maplibre-gl.css"/>
    <style>
            #divMap {
              width: 100%;
              height: 100%;
            }
            .div-icon {
            	background: #fff;
            	border: 1px solid #666;
            	height: auto;
            	width: auto;
            	padding-left: 5px;
            	padding-right: 5px;
            	white-space: nowrap;
            }
          </style>
          <div id="divMap"></div>
    `;
  }

  firstUpdated(changedProperties: any) {
    if (this.renderRoot.querySelector("#divMap") === null) return;
    this.map = new MapLibreComponent({
      container: <HTMLElement>this.renderRoot.querySelector("#divMap"),
      style: {
        version: 8,
        sources: {
          osm: {
            type: "raster",
            tiles: ["https://a.tile.openstreetmap.org/{z}/{x}/{y}.png"],
            tileSize: 256,
            attribution: "&copy; OpenStreetMap Contributors",
            maxzoom: 19,
          },
        },
        layers: [
          {
            id: "osm",
            type: "raster",
            source: "osm", // This must match the source key above
          },
        ],
        glyphs: "http://fonts.openmaptiles.org/{fontstack}/{range}.pbf",
      }, // stylesheet location
      center: [11.9736852, 57.689798], // starting position [lng, lat]
      zoom: 17, // starting zoom
    });

    this.map.once("load", () => {
      this.$server!.onReady();
    });

    this.map.addControl(
      new GeolocateControl({
        positionOptions: {
          enableHighAccuracy: true,
        },
        trackUserLocation: true,
      })
    );
    //this.map.addSource('buildings', {type="geojson", data: "https://maps.chalmers.se/v2/geojson?types%5B%5D=facility%3Aadministrative_office&types%5B%5D=building%3Auniversity"});
  }
}
interface MapLibreServerInterface {
  onReady(): void;
}
customElements.define("maplibre-gl-js", MapLibre);
