import { LitElement, html } from "lit-element";
//import "https://unpkg.com/maplibre-gl@2.4.0/dist/maplibre-gl.js";
import { Map as MapLibreComponent, GeolocateControl } from "maplibre-gl";

const routeID = "route";
const routePathID = "route-path";
const routeDestID = "route-dest";

/**
 * The style that is used for displaying OpenStreetMap tiles on the map.
 */
const openStreetMapStyle = {
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
};

/**
 * The paint style that is used for determining the color and extrusion height of buildings.
 */
const buildingExtrusionPaint = {
  "fill-extrusion-color": "#aaa",
  "fill-extrusion-height": [
    "interpolate",
    ["linear"],
    ["zoom"],
    14,
    0,
    20.05,
    // Ignore type error that works anyway
    // @ts-ignore
    ["get", "height"],
  ],
  "fill-extrusion-base": [
    "interpolate",
    ["linear"],
    ["zoom"],
    14,
    0,
    20.05,
    // Ignore type error that works anyway
    // @ts-ignore
    ["get", "min_height"],
  ],
  "fill-extrusion-opacity": 0.95,
};

const routePathPaint = { "line-color": "#314ccd", "line-width": 8 };
const routeDestPaint = { "circle-radius": 10, "circle-color": "#f4347c" };
const roomPinPaint = { "circle-radius": 10, "circle-color": "#a3446a" };

class MapLibre extends LitElement {
  map!: MapLibreComponent;

  // Must be used because map cannot initialized in constructor
  onReady: Event = new CustomEvent("ready");
  ready: boolean = false;

  /**
   * Removes the currently displayed route from the map.
   */
  removeRoute() {
    if (this.ready) {
      this._removeRoute();
    } else {
      this.addEventListener("ready", () => this._removeRoute(), { once: true });
    }
  }

  private _removeRoute() {
    if (this.map.getLayer(routePathID)) {
      this.map.removeLayer(routePathID);
    }

    if (this.map.getLayer(routeDestID)) {
      this.map.removeLayer(routeDestID);
    }

    if (this.map.getSource(routeID)) {
      this.map.removeSource(routeID);
    }
  }

  /**
   * Shows a route given by GeoJSON data.
   * @param route GeoJSON data of the route to show.
   */
  showRoute(maneuvers: any, finalDestination: any) {
    if (this.ready) {
      this._showRoute(maneuvers, finalDestination);
    } else {
      this.addEventListener(
        "ready",
        () => this._showRoute(maneuvers, finalDestination),
        { once: true }
      );
    }
  }

  private _showRoute(maneuvers: any, finalDestination: any) {
    this.removeRoute();
    // Add GeoJSON source with route points and destination point
    this.map.addSource(routeID, {
      type: "geojson",
      data: {
        type: "FeatureCollection",
        features: [
          {
            type: "Feature",
            properties: {},
            geometry: { type: "LineString", coordinates: maneuvers },
          },
          {
            type: "Feature",
            geometry: { type: "Point", coordinates: finalDestination },
            properties: {},
          },
        ],
      },
    });
    // Add layer with route path line
    this.map.addLayer({
      id: routePathID,
      type: "line",
      source: routeID,
      layout: { "line-join": "round", "line-cap": "round" },
      paint: routePathPaint,
    });
    // Add layer with destination point
    this.map.addLayer({
      id: routeDestID,
      type: "circle",
      source: routeID,
      paint: routeDestPaint,
      filter: ["==", "$type", "Point"],
    });
  }

  /**
   * Removes a room from the map.
   * @param roomID UUID of the room to remove.
   */
  removeRoom(id: String) {
    if (this.ready) {
      this._removeRoom(id);
    } else {
      this.addEventListener("ready", () => this._removeRoom(id), {
        once: true,
      });
    }
  }

  private _removeRoom(id: String) {
    this.map.removeLayer("room-layer-" + id);
    this.map.removeSource("room-" + id);
  }

  /**
   * Display a room on the map.
   * @param room Room to display.
   */
  addRoom(id: string, name: string, latitude: number, longitude: number) {
    if (this.ready) {
      this._addRoom(id, name, latitude, longitude);
    } else {
      this.addEventListener(
        "ready",
        () => this._addRoom(id, name, latitude, longitude),
        { once: true }
      );
    }
  }

  private _addRoom(
    id: string,
    name: string,
    latitude: number,
    longitude: number
  ) {
    // Add GeoJSON source with room point
    this.map.addSource("room-" + id, {
      type: "geojson",
      data: {
        type: "Feature",
        geometry: { type: "Point", coordinates: [longitude, latitude] },
        properties: { name: name },
      },
    });
    // Add layer with room point
    this.map.addLayer({
      id: "room-layer-" + id,
      type: "circle",
      source: "room-" + id,
      paint: roomPinPaint,
    });
  }

  /**
   * Adds a GeoJSON source to the map.
   * @param id ID of the source.
   * @param source GeoJSON source to add.
   */
  addGeoJSON(id: string, source: any) {
    if (this.ready) {
      this._addGeoJSON(id, source);
    } else {
      this.addEventListener("ready", () => this._addGeoJSON(id, source), {
        once: true,
      });
    }
  }

  private _addGeoJSON(id: string, source: any) {
    this.map.addSource(id, { type: "geojson", data: source });
  }

  /**
   * Adds a fill-extrusion layer with given source that contains polygons
   * @param id ID of the layer.
   * @param source ID of the source.
   */
  addExtrudedLayer(id: string, source: any) {
    if (this.ready) {
      this._addExtrudedLayer(id, source);
    } else {
      this.addEventListener("ready", () => this._addExtrudedLayer(id, source), {
        once: true,
      });
    }
  }

  private _addExtrudedLayer(id: string, source: any) {
    this.map.addLayer({
      id: id,
      type: "fill-extrusion",
      source: source,
      layout: {},
      // Suppress typing error that works anyway
      // @ts-ignore
      paint: buildingExtrusionPaint,
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
    </style>
    <div id="divMap"></div>
    `;
  }

  firstUpdated(changedProperties: any) {
    // If there is no div yet, don't load the map
    if (this.renderRoot.querySelector("#divMap") === null) return;

    // Create map component
    this.map = new MapLibreComponent({
      container: <HTMLElement>this.renderRoot.querySelector("#divMap"),
      // Suppress typing error that works anyway
      // @ts-ignore
      style: openStreetMapStyle, // stylesheet location
      center: [11.9736852, 57.689798], // starting position [lng, lat]
      zoom: 17, // starting zoom
    });

    // Add geolocation control
    let geoTracker = new GeolocateControl({
      positionOptions: {
        enableHighAccuracy: true,
      },
      trackUserLocation: true,
    });
    this.map.addControl(geoTracker);

    // Make sure sources and layers are loaded after map is loaded
    this.map.once("load", () => {
      this.ready = true;
      this.dispatchEvent(this.onReady);
      geoTracker.trigger();
      this.map.loadImage("/icons/map-pin.png", (error, image) => {
        if (error) throw error;
        // Suppress typing error that works anyway
        // @ts-ignore
        this.map.addImage("pin", image);
      });
      this.dispatchEvent(this.onPinReady);
    });
  }
}
customElements.define("maplibre-gl-js", MapLibre);
