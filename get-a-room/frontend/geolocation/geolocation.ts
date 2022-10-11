import { LitElement, html, property } from "lit-element";

const geoOptions = {
  enableHighAccuracy: false,
  timeout: 5000,
  maximumAge: 0
};

class GeoLocation extends LitElement {

    private latitude: number | null;
    private longitude: number | null;

    private $server?: GeoLocationServerInterface;

    constructor() {
        super();
        var id = navigator.geolocation.watchPosition((geo) => this._geoSuccess(geo), (err) => this._geoError(err), geoOptions);
        this.latitude = null;
        this.longitude = null;
    }

    render() {
        return html``;
    }

    /**
     * Attempts to get the user location
     */
    updateLocation() {
        navigator.geolocation.getCurrentPosition((geo) => this._geoSuccess(geo), (err) => this._geoError(err), geoOptions);
    }

    /**
     * Gets the latitude of the user
     */
    getLatitude() {
        
        return this.latitude;
    }

    /**
     * Gets the longitude of the user
     */
    getLongitude() {
        console.log(this.longitude);
        return this.longitude;
    }

    private _geoSuccess(position: GeolocationPosition) {
        // Set attribute
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
        //this.longitude = position.coords.longitude;
        this.$server!.onUpdate();
    }

    private _geoError(error: any) {
        // Set attribute
        this.latitude = null;
        this.longitude = null;
        this.$server!.onError();
    }
}

/**
 * Interface for calling server functions
 */
interface GeoLocationServerInterface {
    onUpdate(): void;
    onError(): void;
}

customElements.define("geoloc-custom", GeoLocation);
